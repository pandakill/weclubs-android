package com.mm.weclubs.app.mission_list;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.network.bean.WCMissionListBean;
import com.mm.weclubs.data.network.pojo.WCMissionListInfo;
import com.mm.weclubs.util.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/4 上午11:17
 * 描述:
 */

public class WCMissionListPresenter<V extends WCMissionListContract.View> extends BasePresenter<V>
    implements WCMissionListContract.Presenter<V>{

    @Inject
    public WCMissionListPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void refresh(long clubId) {
        getMvpView().showProgressDialog("加载中...", false);

        Map<String, Object> params = new HashMap<>();

        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
        params.put("page_no", 1);
        params.put("club_id", clubId);
        params.put("dynamic_type", WCConstantsUtil.DYNAMIC_TYPE_MISSION);

        getCompositeDisposable().add(getDataManager().getMissionList(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<WCMissionListBean>() {
                    @Override
                    public void accept(@NonNull WCMissionListBean wcMissionListBean) throws Exception {
                        getMvpView().refreshMissionList(wcMissionListBean.getMission());
                        getMvpView().hideProgressDialog();
                    }
                }));
    }

    public void getMissionListFromServer(long clubId, final int pageNo) {

        getMvpView().showProgressDialog("加载中...", false);

        HashMap<String, Object> params = new HashMap<>();

        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
        params.put("page_no", pageNo);
        params.put("club_id", clubId);
        params.put("dynamic_type", WCConstantsUtil.DYNAMIC_TYPE_MISSION);

        getCompositeDisposable().add(getDataManager().getMissionList(params)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe(new Consumer<WCMissionListBean>() {
                @Override
                public void accept(@NonNull WCMissionListBean wcMissionListBean) throws Exception {
                    getMvpView().addMissionList(wcMissionListBean.getMission(), wcMissionListBean.getHas_more() == 1);
                    getMvpView().hideProgressDialog();
                }
            },new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    WCMissionListPresenter.this.accept(throwable);
                    if (isViewAttachView()){
                        //加载更多失败
                        getMvpView().loadFail();
                    }
                }
            }));
    }



    public void setMissionConfirm(long missionId,final  int position,final  WCMissionListInfo missionListInfo) {

        getMvpView().showProgressDialog("加载中...", false);

        HashMap<String, Object> params = new HashMap<>();

        params.put("dynamic_id", missionId);
        params.put("dynamic_type", WCConstantsUtil.DYNAMIC_TYPE_MISSION);
        params.put("status", "confirm");

        getCompositeDisposable().add(getDataManager().setMissionStatus(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        getMvpView().hideProgressDialog();

                        getMvpView().showToast("任务确认成功");
                        if (missionListInfo != null) {
                            missionListInfo.setConfirm_receive(1);
                            getMvpView().notifyChangeList(missionListInfo, position);
                        }
                    }
                },this));
    }
}
