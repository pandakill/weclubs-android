package com.mm.weclubs.app.manage.notify;

import com.blankj.utilcode.utils.EmptyUtils;
import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.db.entity.User;
import com.mm.weclubs.data.network.bean.WCCommentListBean;
import com.mm.weclubs.data.network.pojo.WCManageNotifyInfo;
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
 * 创建时间: 2017/5/15 下午6:54
 * 描述:
 */

public class WCManageNotifyDetailPresenter<V extends WCManageNotifyDetailContract.View> extends BasePresenter<V>
    implements WCManageNotifyDetailContract.Presenter<V>{

    @Inject
    public WCManageNotifyDetailPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getNotifyDetailFromServer(final long notifyId) {

        getCompositeDisposable().add(Observable.just(getDataManager().getLastTimeLoginId())
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer) throws Exception {
                        User user = getDataManager().loadUser();
                        if (user == null || user.getUserId() != integer){
                            return NULL_INDEX;
                        }
                        return user.getUserId();
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .flatMap(new Function<Integer, ObservableSource<WCManageNotifyInfo>>() {
                    @Override
                    public ObservableSource<WCManageNotifyInfo> apply(@NonNull Integer userId) throws Exception {
                        if (userId == NULL_INDEX){
                            getMvpView().showToast("用户没有登录");
                            getMvpView().backToLoginActivity();
                            return Observable.empty();
                        }
                        getMvpView().showProgressDialog("加载中...", false);

                        Map<String, Object> params = new HashMap<>();

                        params.put("notify_id", notifyId);

                        return getDataManager().getMyNotifyDetail(params)
                                .subscribeOn(getSchedulerProvider().io());
                    }
                }).observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<WCManageNotifyInfo>() {
                    @Override
                    public void accept(@NonNull WCManageNotifyInfo wcClubNotifyBean) throws Exception {
                        getMvpView().getNotifyDetailSuccess(wcClubNotifyBean);
                        getMvpView().hideProgressDialog();
                    }
                },this));
    }

    @Override
    public void getCommentList(String type, long sourceId, final int pageNo) {
        if (EmptyUtils.isEmpty(type)) {
            KLog.e("type类型不能为空");
            return;
        }

        if (sourceId <= 0) {
            KLog.e("sourceId不能小于等于0");
            return;
        }

        if (!WCConstantsUtil.DYNAMIC_TYPE_MEETING.equals(type)
                && !WCConstantsUtil.DYNAMIC_TYPE_ACTIVITY.equals(type)
                && !WCConstantsUtil.DYNAMIC_TYPE_MISSION.equals(type)
                && !WCConstantsUtil.DYNAMIC_TYPE_NOTIFY.equals(type)) {
            KLog.e("传入的 type 类型不正确，传入的类型为：" + type);
            return;
        }

        getMvpView().showProgressDialog("加载中...", false);

        Map<String, Object> params = new HashMap<>();
        params.put("source_type", type);
        params.put("source_id", sourceId);
        params.put("page_no", pageNo);
        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);

        getCompositeDisposable().add(getDataManager()
                .getCommentList(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<WCCommentListBean>() {
                    @Override
                    public void accept(@NonNull WCCommentListBean wcCommentListBean) throws Exception {
                        if (pageNo == 1){
                            getMvpView().refreshCommentList(wcCommentListBean.getComment());
                        }else{
                            getMvpView().addCommentList(wcCommentListBean.getComment(),wcCommentListBean.getHas_more() == 1);
                        }
                        getMvpView().hideProgressDialog();
                    }
                },this));
    }
}
