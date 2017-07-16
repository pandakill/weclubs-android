package com.mm.weclubs.app.login;

import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.RegexUtils;
import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.db.entity.User;
import com.mm.weclubs.data.network.pojo.WCUserInfoInfo;
import com.mm.weclubs.util.DataHelper;
import com.mm.weclubs.util.MD5Util;
import com.mm.weclubs.util.rx.SchedulerProvider;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.mm.weclubs.data.prefs.AppPreferencesHelper.NULL_INDEX;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/21 下午11:29
 * 描述: 用于登录相关的 presenter
 */

public class WCLoginPresenter<V extends WCLoginContract.View> extends BasePresenter<V>
    implements WCLoginContract.Presenter<V>{

    @Inject
    public WCLoginPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void attachView(V mvpView) {
        super.attachView(mvpView);

        final int lastLoginId = getDataManager().getLastTimeLoginId();

        if (lastLoginId <= NULL_INDEX){
            return;
        }

        getCompositeDisposable().add(
                Observable.just(lastLoginId)
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                        User user = getDataManager().loadUser();
                        if (user == null || user.getUserId() != integer){
                            return Observable.empty();
                        }
                        return Observable.just(user.getMobile());
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String mobile) throws Exception {
                        getMvpView().setDefaultMobile(mobile);
                    }
                }));
    }

    /**
     * 通过手机号码和密码登录
     *
     * @param mobile    手机号码
     * @param password  密码
     */
    @Override
    public void login(String mobile, String password) {

        if (EmptyUtils.isEmpty(mobile)) {
            getMvpView().loginFail("手机号码不能为空");
            return;
        }

        if (EmptyUtils.isEmpty(password)) {
            getMvpView().loginFail("密码不能为空");
            return;
        }

        if (!RegexUtils.isMobileSimple(mobile)) {
            getMvpView().loginFail("请输入正确的手机号码");
            return;
        }

        getMvpView().showProgressDialog("登录中...", false);

        KLog.d("mobile = " + mobile + "; password = " + password);
        password = MD5Util.md5(password);

        HashMap<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", password);

        getCompositeDisposable().add(getDataManager().login(params)
                .doOnNext(new Consumer<WCUserInfoInfo>() {
                    @Override
                    public void accept(@NonNull WCUserInfoInfo wcUserInfoInfo) throws Exception {
                        getDataManager().saveUser(DataHelper.wcUserInfoInfo2User(wcUserInfoInfo));
                        getDataManager().saveLastTimeLoginId(wcUserInfoInfo.getUser_id());
                    }
                })
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe(new Consumer<WCUserInfoInfo>() {
                @Override
                public void accept(@NonNull WCUserInfoInfo wcUserInfoInfo) throws Exception {
                    getMvpView().hideProgressDialog();
                    getMvpView().loginSuccess(wcUserInfoInfo);
                }
            }, this));
    }

    @Override
    public void checkLogin() {

        getCompositeDisposable().add(Observable.just(getDataManager().getLastTimeLoginId())
                .flatMap(new Function<Integer, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(@NonNull Integer integer) throws Exception {
                        if (integer <= NULL_INDEX){
                            //没有最后一个登录的ID
                            KLog.d("没有最后一个登录的ID");
                            return Observable.empty();
                        }
                        User user = getDataManager().loadUser();

                        if (user == null || user.getUserId() != integer){
                            //没有找到数据
                            KLog.d("没有找到数据");
                            return Observable.empty();
                        }

                        return Observable.just(user);
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .flatMap(new Function<User, ObservableSource<WCUserInfoInfo>>() {
                    @Override
                    public ObservableSource<WCUserInfoInfo> apply(@NonNull User user) throws Exception {
                        getMvpView().showProgressDialog("登录中...", false);
                        Map<String,Object> params = new HashMap<>();

                        params.put("mobile",user.getMobile());
                        params.put("token",user.getToken());
                        params.put("user_id",user.getUserId());

                        return getDataManager().getUserInfo(params)
                                .subscribeOn(getSchedulerProvider().io());
                    }
                })
                .observeOn(getSchedulerProvider().io())
                .doOnNext(new Consumer<WCUserInfoInfo>() {
                    @Override
                    public void accept(@NonNull WCUserInfoInfo wcUserInfoInfo) throws Exception {
                        User user = getDataManager().loadUser();
                        wcUserInfoInfo.setToken(user.getToken());
                        //保存数据
                        KLog.d("保存数据");
                        getDataManager().saveUser(DataHelper.wcUserInfoInfo2User(wcUserInfoInfo));
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //清除用户数据?
                        KLog.d("清除用户数据");
                        getDataManager().clearUser();
                    }
                })
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<WCUserInfoInfo>() {
                    @Override
                    public void accept(@NonNull WCUserInfoInfo wcUserInfoInfo) throws Exception {
                        KLog.d("登录成功");
                        getMvpView().hideProgressDialog();
                        getMvpView().loginSuccess(wcUserInfoInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (isViewAttachView()){
                            getMvpView().hideProgressDialog();
                        }
                    }
                }));
    }
}
