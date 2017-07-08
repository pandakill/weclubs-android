package com.mm.weclubs.app.notify_list;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.network.bean.WCNotifyListBean;
import com.mm.weclubs.data.network.pojo.WCNotifyListInfo;
import com.mm.weclubs.util.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/12 下午5:33
 * 描述:
 */

public class WCNotifyListPresenter<V extends WCNotifyListContract.View> extends BasePresenter<V>
    implements WCNotifyListContract.Presenter<V>{

    @Inject
    public WCNotifyListPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void refresh(long clubId) {
        getMvpView().showProgressDialog("注册中...", false);

        Map<String, Object> params = new HashMap<>();

        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
        params.put("page_no", 1);
        params.put("club_id", clubId);
        params.put("dynamic_type", WCConstantsUtil.DYNAMIC_TYPE_NOTIFY);

        getCompositeDisposable().add(getDataManager().getNotifyList(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<WCNotifyListBean>() {
                    @Override
                    public void accept(@NonNull WCNotifyListBean wcNotifyListBean) throws Exception {
                        getMvpView().refreshNotifyList(wcNotifyListBean.getNotify());
                        getMvpView().hideProgressDialog();
                    }
                },this));
    }

    @Override
    public void getNotifyFromServer(long clubId,final  int pageNo) {

        Map<String, Object> params = new HashMap<>();

        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
        params.put("page_no", pageNo);
        params.put("club_id", clubId);
        params.put("dynamic_type", WCConstantsUtil.DYNAMIC_TYPE_NOTIFY);

        getCompositeDisposable().add(getDataManager().getNotifyList(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<WCNotifyListBean>() {
                    @Override
                    public void accept(@NonNull WCNotifyListBean wcNotifyListBean) throws Exception {
                        getMvpView().addNotifyList(wcNotifyListBean.getNotify(), wcNotifyListBean.getHas_more() == 1);
                        getMvpView().hideProgressDialog();
                    }
                },new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        WCNotifyListPresenter.this.accept(throwable);
                        if (isViewAttachView()){
                            //加载更多失败
                            getMvpView().loadFail();
                        }
                    }
                }));
    }

    @Override
    public void setNotifyConfirm(long notifyId,final  int position,final  WCNotifyListInfo notifyListInfo) {

        getMvpView().showProgressDialog("加载中...", false);

        HashMap<String, Object> params = new HashMap<>();

        params.put("dynamic_id", notifyId);
        params.put("dynamic_type", WCConstantsUtil.DYNAMIC_TYPE_NOTIFY);
        params.put("status", "confirm");

        getCompositeDisposable().add(getDataManager().setMissionStatus(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object object) throws Exception {
                        getMvpView().hideProgressDialog();
                        getMvpView().showToast("通知确认成功");
                        if (notifyListInfo != null) {
                            notifyListInfo.setConfirm_receive(1);
                            getMvpView().notifyChangeList(notifyListInfo, position);
                        }
                    }
                },this));
    }
}
