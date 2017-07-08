package com.mm.weclubs.data;

import android.support.annotation.NonNull;

import com.mm.weclubs.data.db.DbHelper;
import com.mm.weclubs.data.db.entity.User;
import com.mm.weclubs.data.network.ApiHelper;
import com.mm.weclubs.data.network.bean.WCClubMeetingBean;
import com.mm.weclubs.data.network.bean.WCClubMissionBean;
import com.mm.weclubs.data.network.bean.WCClubNotifyBean;
import com.mm.weclubs.data.network.bean.WCCommentListBean;
import com.mm.weclubs.data.network.bean.WCMeetingListBean;
import com.mm.weclubs.data.network.bean.WCMeetingParticipationBean;
import com.mm.weclubs.data.network.bean.WCMissionListBean;
import com.mm.weclubs.data.network.bean.WCMyClubsBean;
import com.mm.weclubs.data.network.bean.WCNotifyCheckStatusBean;
import com.mm.weclubs.data.network.bean.WCNotifyListBean;
import com.mm.weclubs.data.network.pojo.WCManageMeetingDetailInfo;
import com.mm.weclubs.data.network.pojo.WCManageNotifyInfo;
import com.mm.weclubs.data.network.pojo.WCMeetingDetailInfo;
import com.mm.weclubs.data.network.pojo.WCMissionDetailInfo;
import com.mm.weclubs.data.network.pojo.WCNotifyListInfo;
import com.mm.weclubs.data.network.pojo.WCUserInfoInfo;
import com.mm.weclubs.data.prefs.PreferencesHelper;
import com.socks.library.KLog;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.mm.weclubs.data.prefs.AppPreferencesHelper.NULL_INDEX;

/**
 * 文 件 名: AppDataManager
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/6 21:44
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */
@Singleton
public class AppDataManager implements DataManager {

    private final DbHelper mDbHelper;
    private final ApiHelper mApiHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(DbHelper dbHelper, ApiHelper apiHelper, PreferencesHelper preferencesHelper) {
        mDbHelper = dbHelper;
        mApiHelper = apiHelper;
        mPreferencesHelper = preferencesHelper;
    }

    // =============  DataManager =============

    @Override
    public Observable<Integer> getUserId() {
        return Observable.just(getLastTimeLoginId())
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer) throws Exception {
                        User user = loadUser();
                        if (user == null || user.getUserId() != integer){
                            return NULL_INDEX;
                        }
                        return user.getUserId();
                    }
                });
    }


    // ============ DB =======================

    @Override
    public void saveUser(User user) {
        KLog.d(user.getMobile());
        KLog.d("保存用户");
        mDbHelper.saveUser(user);
    }

    @Override
    public void clearUser() {
        KLog.d("清除用户");
        mDbHelper.clearUser();
    }

    @Override
    public User loadUser() {
        KLog.d("加载用户");
        return mDbHelper.loadUser();
    }

    // =============== Prefs =====================

    @Override
    public void saveLastTimeLoginId(int id) {
        mPreferencesHelper.saveLastTimeLoginId(id);
    }

    @Override
    public int getLastTimeLoginId() {
        return mPreferencesHelper.getLastTimeLoginId();
    }

    @Override
    public void saveUUid(String uuid) {
        mPreferencesHelper.saveUUid(uuid);
    }

    @Override
    public String getUUid() {
        return mPreferencesHelper.getUUid();
    }

    // ===================== Network =====================
    @Override
    public Observable<WCUserInfoInfo> login(@NonNull Map<String, Object> params) {
        //登录肯定无状态
        return mApiHelper.login(params);
    }

    @Override
    public Observable<WCUserInfoInfo> register(@NonNull Map<String, Object> params) {
        //注册同样无状态
        return mApiHelper.register(params);
    }

    @Override
    public Observable<WCUserInfoInfo> getUserInfo(@NonNull Map<String, Object> params) {
        // 已经携带状态消息(已经传入user_id,token)
        return mApiHelper.getUserInfo(params);
    }

    @Override
    public Observable<WCMyClubsBean> getMyClubs(@NonNull Map<String, Object> params) {
        // 如果已经登录，需要传入user_id,token

        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCMyClubsBean>>() {
                    @Override
                    public ObservableSource<WCMyClubsBean> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getMyClubs(addUserParams);
                    }
                });
    }

    @Override
    public Observable<WCMeetingListBean> getMeetingList(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCMeetingListBean>>() {
                    @Override
                    public ObservableSource<WCMeetingListBean> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getMeetingList(addUserParams);
                    }
                });
    }

    @Override
    public Observable<WCMeetingDetailInfo> getMeetingDetail(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCMeetingDetailInfo>>() {
                    @Override
                    public ObservableSource<WCMeetingDetailInfo> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getMeetingDetail(addUserParams);
                    }
                });
    }

    @Override
    public Observable<WCMissionListBean> getMissionList(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCMissionListBean>>() {
                    @Override
                    public ObservableSource<WCMissionListBean> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getMissionList(addUserParams);
                    }
                });
    }

    @Override
    public Observable<WCMissionDetailInfo> getMissionDetail(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCMissionDetailInfo>>() {
                    @Override
                    public ObservableSource<WCMissionDetailInfo> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getMissionDetail(addUserParams);
                    }
                });
    }

    @Override
    public Observable<WCNotifyListBean> getNotifyList(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCNotifyListBean>>() {
                    @Override
                    public ObservableSource<WCNotifyListBean> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getNotifyList(addUserParams);
                    }
                });
    }

    @Override
    public Observable<WCNotifyListInfo> getNotifyDetail(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCNotifyListInfo>>() {
                    @Override
                    public ObservableSource<WCNotifyListInfo> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getNotifyDetail(addUserParams);
                    }
                });
    }

    @Override
    public Observable<Object> setMissionStatus(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.setMissionStatus(addUserParams);
                    }
                });
    }

    @Override
    public Observable<WCCommentListBean> getCommentList(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCCommentListBean>>() {
                    @Override
                    public ObservableSource<WCCommentListBean> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getCommentList(addUserParams);
                    }
                });
    }

    @Override
    public Observable<WCClubNotifyBean> getMyNotify(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCClubNotifyBean>>() {
                    @Override
                    public ObservableSource<WCClubNotifyBean> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getMyNotify(addUserParams);
                    }
                });
    }

    @Override
    public Observable<WCManageNotifyInfo> getMyNotifyDetail(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCManageNotifyInfo>>() {
                    @Override
                    public ObservableSource<WCManageNotifyInfo> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getMyNotifyDetail(addUserParams);
                    }
                });
    }

    @Override
    public Observable<WCNotifyCheckStatusBean> getNotifyCheckStatusList(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCNotifyCheckStatusBean>>() {
                    @Override
                    public ObservableSource<WCNotifyCheckStatusBean> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getNotifyCheckStatusList(addUserParams);
                    }
                });
    }

    @Override
    public Observable<WCClubMeetingBean> getMyMeeting(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCClubMeetingBean>>() {
                    @Override
                    public ObservableSource<WCClubMeetingBean> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getMyMeeting(addUserParams);
                    }
                });
    }

    @Override
    public Observable<WCManageMeetingDetailInfo> getMyMeetingDetail(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCManageMeetingDetailInfo>>() {
                    @Override
                    public ObservableSource<WCManageMeetingDetailInfo> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getMyMeetingDetail(addUserParams);
                    }
                });
    }

    @Override
    public Observable<WCMeetingParticipationBean> getMeetingParticipation(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCMeetingParticipationBean>>() {
                    @Override
                    public ObservableSource<WCMeetingParticipationBean> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getMeetingParticipation(addUserParams);
                    }
                });
    }

    @Override
    public Observable<WCClubMissionBean> getMyMission(@NonNull Map<String, Object> params) {
        return addUserInfo(params)
                .flatMap(new Function<Map<String, Object>, ObservableSource<WCClubMissionBean>>() {
                    @Override
                    public ObservableSource<WCClubMissionBean> apply(@io.reactivex.annotations.NonNull Map<String, Object> addUserParams) throws Exception {
                        return mApiHelper.getMyMission(addUserParams);
                    }
                });
    }

    /*
                 * 添加公共参数
                 */
    private Observable<Map<String,Object>> addUserInfo(@NonNull Map<String, Object> params){
        // 查询数据库，找到用户信息
        return Observable.just(params)
                .map(new Function<Map<String, Object>, Map<String, Object>>() {
                    @Override
                    public Map<String, Object> apply(@io.reactivex.annotations.NonNull Map<String, Object> map) throws Exception {
                        int userId = getLastTimeLoginId();
                        User user = loadUser();
                        if (user == null || user.getUserId() != userId){
                            //没有登录状态，或者登录状态异常
                            return map;
                        }
                        map.put("user_id",user.getUserId());
                        map.put("token",user.getToken());
                        return map;
                    }
                });
    }
}
