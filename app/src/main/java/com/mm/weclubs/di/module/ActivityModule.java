/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.mm.weclubs.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.mm.weclubs.app.club.WCMyClubListContract;
import com.mm.weclubs.app.club.WCMyClubListPresenter;
import com.mm.weclubs.app.index.WCIndexContract;
import com.mm.weclubs.app.index.WCIndexPresenter;
import com.mm.weclubs.app.login.WCLoginContract;
import com.mm.weclubs.app.login.WCLoginPresenter;
import com.mm.weclubs.app.manage.meeting.WCManageMeetingDetailContract;
import com.mm.weclubs.app.manage.meeting.WCManageMeetingDetailPresenter;
import com.mm.weclubs.app.manage.meeting.WCManageMeetingListContract;
import com.mm.weclubs.app.manage.meeting.WCManageMeetingListPresenter;
import com.mm.weclubs.app.manage.meeting.WCMeetingParticipationDetailContract;
import com.mm.weclubs.app.manage.meeting.WCMeetingParticipationDetailPresenter;
import com.mm.weclubs.app.manage.mission.WCManageMissionListContract;
import com.mm.weclubs.app.manage.mission.WCManageMissionListPresenter;
import com.mm.weclubs.app.manage.notify.WCManageNotifyDetailContract;
import com.mm.weclubs.app.manage.notify.WCManageNotifyDetailPresenter;
import com.mm.weclubs.app.manage.notify.WCManageNotifyListContract;
import com.mm.weclubs.app.manage.notify.WCManageNotifyListPresenter;
import com.mm.weclubs.app.manage.notify.WCNotifyReceiveStatusContract;
import com.mm.weclubs.app.manage.notify.WCNotifyReceiveStatusPresenter;
import com.mm.weclubs.app.meeting_detail.WCMeetingDetailContract;
import com.mm.weclubs.app.meeting_detail.WCMeetingDetailPresenter;
import com.mm.weclubs.app.meeting_list.WCMeetingListContract;
import com.mm.weclubs.app.meeting_list.WCMeetingListPresenter;
import com.mm.weclubs.app.mission_detail.WCMissionDetailContract;
import com.mm.weclubs.app.mission_detail.WCMissionDetailPresenter;
import com.mm.weclubs.app.mission_list.WCMissionListContract;
import com.mm.weclubs.app.mission_list.WCMissionListPresenter;
import com.mm.weclubs.app.notify_detail.WCNotifyDetailContract;
import com.mm.weclubs.app.notify_detail.WCNotifyDetailPresenter;
import com.mm.weclubs.app.notify_list.WCNotifyListContract;
import com.mm.weclubs.app.notify_list.WCNotifyListPresenter;
import com.mm.weclubs.app.register.WCRegisterContract;
import com.mm.weclubs.app.register.WCRegisterPresenter;
import com.mm.weclubs.di.ActivityContext;
import com.mm.weclubs.di.ActivityGlide;
import com.mm.weclubs.di.PerActivity;
import com.mm.weclubs.glide.okhttp3.GlideApp;
import com.mm.weclubs.glide.okhttp3.GlideRequests;
import com.mm.weclubs.util.rx.AppSchedulerProvider;
import com.mm.weclubs.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    private final GlideRequests mGlideRequests;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
        mGlideRequests = GlideApp.with(activity);
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    @ActivityGlide
    GlideRequests provideGlideRequests(){
        return mGlideRequests;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    WCLoginContract.Presenter<WCLoginContract.View> provideWCLoginPresenter(WCLoginPresenter<WCLoginContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCRegisterContract.Presenter<WCRegisterContract.View> provideWCRegisterPresenter(WCRegisterPresenter<WCRegisterContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCMyClubListContract.Presenter<WCMyClubListContract.View> provideWCMyClubListPresenter(WCMyClubListPresenter<WCMyClubListContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCNotifyListContract.Presenter<WCNotifyListContract.View> provideWCNotifyListPresenter(WCNotifyListPresenter<WCNotifyListContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCNotifyDetailContract.Presenter<WCNotifyDetailContract.View> provideWCNotifyDetailPresenter(WCNotifyDetailPresenter<WCNotifyDetailContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCMissionDetailContract.Presenter<WCMissionDetailContract.View> provideWCMissionDetailPresenter(WCMissionDetailPresenter<WCMissionDetailContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCMeetingListContract.Presenter<WCMeetingListContract.View> provideWCMeetingListPresenter(WCMeetingListPresenter<WCMeetingListContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCMeetingDetailContract.Presenter<WCMeetingDetailContract.View> provideWCMeetingDetailPresenter(WCMeetingDetailPresenter<WCMeetingDetailContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCManageNotifyListContract.Presenter<WCManageNotifyListContract.View> provideWCManageNotifyListPresenter(WCManageNotifyListPresenter<WCManageNotifyListContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCManageNotifyDetailContract.Presenter<WCManageNotifyDetailContract.View> provideWCManageNotifyDetailPresenter(WCManageNotifyDetailPresenter<WCManageNotifyDetailContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCNotifyReceiveStatusContract.Presenter<WCNotifyReceiveStatusContract.View> provideWCNotifyReceiveStatusPresenter(WCNotifyReceiveStatusPresenter<WCNotifyReceiveStatusContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCManageMeetingListContract.Presenter<WCManageMeetingListContract.View> provideWCManageMeetingListPresenter(WCManageMeetingListPresenter<WCManageMeetingListContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCManageMeetingDetailContract.Presenter<WCManageMeetingDetailContract.View> provideWCManageMeetingDetailPresenter(WCManageMeetingDetailPresenter<WCManageMeetingDetailContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCMeetingParticipationDetailContract.Presenter<WCMeetingParticipationDetailContract.View> provideWCMeetingParticipationDetailPresenter(WCMeetingParticipationDetailPresenter<WCMeetingParticipationDetailContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCMissionListContract.Presenter<WCMissionListContract.View> provideWCMissionListPresenter(WCMissionListPresenter<WCMissionListContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCManageMissionListContract.Presenter<WCManageMissionListContract.View> provideWCManageMissionListPresenter(WCManageMissionListPresenter<WCManageMissionListContract.View> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    WCIndexContract.Presenter<WCIndexContract.View> provideWCIndexPresenter(WCIndexPresenter<WCIndexContract.View> presenter){
        return presenter;
    }
}
