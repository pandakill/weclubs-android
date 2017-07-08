package com.mm.weclubs.rxbus;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.Functions;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 文 件 名: RxBus
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/5/1 20:55
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public final class RxBus {

    private static volatile RxBus sBus;

    private Subject<Object> mSubject;

    private ArrayMap<String,CompositeDisposable> mDisposableMap;

    private ArrayMap<String,Object> mStickyMap;

    private final Object mLock = new Object();

    private RxBus() {
        mSubject = PublishSubject.create().toSerialized();
    }

    //单例
    public static RxBus getDefault(){
        if (sBus == null) {
            synchronized (RxBus.class) {
                if (sBus == null) {
                    sBus = new RxBus();
                }
            }
        }

        return sBus;
    }

    public void post(Object o){
        mSubject.onNext(o);
    }

    public void postSticky(Object o){
        if (mStickyMap == null){
            mStickyMap = new ArrayMap<>();
        }
        synchronized (mLock) {
            mStickyMap.put(o.getClass().getName(), o);
        }
        mSubject.onNext(o);
    }

    public <T> T getStickyEvent(Class<T> type){
        if (mStickyMap == null){
            return null;
        }
        synchronized (mLock){
            return type.cast(mStickyMap.get(type.getName()));
        }
    }

    public <T> T removeStickyEvent(Class<T> type){
        if (mStickyMap == null){
            return null;
        }
        synchronized (mLock){
            return type.cast(mStickyMap.remove(type.getName()));
        }
    }

    public void removeAllStickyEvent(){
        if (mStickyMap == null){
            return;
        }
        synchronized (mLock){
            mStickyMap.clear();
        }
    }

    public <T> Observable<T> toObservable(@NonNull final Class<T> type){
        return mSubject.ofType(type);
    }

    public <T> Disposable doSubScribe(@NonNull Class<T> type, @NonNull Consumer<T> next){
        return doSubScribe(type,next, Functions.ON_ERROR_MISSING);
    }

    public <T> Disposable doSubScribe(@NonNull Class<T> type, @NonNull Consumer<T> next, @NonNull Consumer<Throwable> error){
        LambdaObserver<T> ls = new LambdaObserver<T>(next, error, Functions.EMPTY_ACTION, Functions.<Disposable>emptyConsumer());

        toObservable(type).subscribe(ls);

        return ls;
    }

    public <T> Disposable doSubscribeSticky(@NonNull final Class<T> type, @NonNull Consumer<T> next, @NonNull Consumer<Throwable> error){
        if (mStickyMap == null){
            return doSubScribe(type,next,error);
        }

        synchronized (mLock){

            final Object event = mStickyMap.get(type.getName());

            if (event == null){
                return doSubScribe(type,next,error);
            }

            return mSubject.ofType(type)
                    .mergeWith(Observable.create(new ObservableOnSubscribe<T>() {
                        @Override
                        public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<T> e) throws Exception {
                            e.onNext(type.cast(event));
                        }
                    }))
                    .subscribe(next,error);
        }
    }

    public void addDisposable(@NonNull Object o,@NonNull Disposable disposable){
        if (mDisposableMap == null){
            mDisposableMap = new ArrayMap<>();
        }
        String key = o.getClass().getName();
        if (mDisposableMap.get(key) == null){
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(disposable);
            mDisposableMap.put(key,compositeDisposable);
        } else {
            mDisposableMap.get(key).add(disposable);
        }
    }

    public void dispose(@NonNull Object o){
        if (mDisposableMap == null){
            return;
        }

        String key = o.getClass().getName();
        if (!mDisposableMap.containsKey(key)){
            return;
        }

        CompositeDisposable compositeDisposable = mDisposableMap.get(key);

        if (compositeDisposable != null){
            compositeDisposable.dispose();
        }

        mDisposableMap.remove(key);
    }

    final class LambdaObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {

        private static final long serialVersionUID = -7251123623727029452L;
        final Consumer<? super T> onNext;
        final Consumer<? super Throwable> onError;
        final Action onComplete;
        final Consumer<? super Disposable> onSubscribe;

        public LambdaObserver(Consumer<? super T> onNext, Consumer<? super Throwable> onError,
                              Action onComplete,
                              Consumer<? super Disposable> onSubscribe) {
            super();
            this.onNext = onNext;
            this.onError = onError;
            this.onComplete = onComplete;
            this.onSubscribe = onSubscribe;
        }

        @Override
        public void onSubscribe(Disposable s) {
            if (DisposableHelper.setOnce(this, s)) {
                try {
                    onSubscribe.accept(this);
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    s.dispose();
                    onError(ex);
                }
            }
        }

        @Override
        public void onNext(T t) {
            if (!isDisposed()) {
                try {
                    onNext.accept(t);
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    //get().dispose();
                    onError(e);
                }
            }
        }

        @Override
        public void onError(Throwable t) {
            if (!isDisposed()) {
                //lazySet(DisposableHelper.DISPOSED);
                try {
                    onError.accept(t);
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    RxJavaPlugins.onError(new CompositeException(t, e));
                }
            }
        }

        @Override
        public void onComplete() {
            if (!isDisposed()) {
                //lazySet(DisposableHelper.DISPOSED);
                try {
                    onComplete.run();
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    RxJavaPlugins.onError(e);
                }
            }
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override
        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }
    }
}
