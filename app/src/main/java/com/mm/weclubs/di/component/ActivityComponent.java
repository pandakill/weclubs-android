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

package com.mm.weclubs.di.component;


import com.mm.weclubs.di.PerActivity;
import com.mm.weclubs.di.module.ActivityModule;
import com.mm.weclubs.ui.activity.WCLoginActivity;
import com.mm.weclubs.ui.activity.WCMeetingDetailActivity;
import com.mm.weclubs.ui.activity.WCMissionDetailActivity;
import com.mm.weclubs.ui.activity.WCNotifyDetailActivity;
import com.mm.weclubs.ui.activity.WCRegisterActivity;
import com.mm.weclubs.ui.activity.manage.WCMeetingManageDetailActivity;
import com.mm.weclubs.ui.activity.manage.WCMeetingManageListActivity;
import com.mm.weclubs.ui.activity.manage.WCMeetingParticipationDetailActivity;
import com.mm.weclubs.ui.activity.manage.WCMissionManageListActivity;
import com.mm.weclubs.ui.activity.manage.WCNotifyManageDetailActivity;
import com.mm.weclubs.ui.activity.manage.WCNotifyManageListActivity;
import com.mm.weclubs.ui.activity.manage.WCNotifyReceiveStatusActivity;
import com.mm.weclubs.ui.fragment.WCDynamicFragment;
import com.mm.weclubs.ui.fragment.WCIndexFragment;
import com.mm.weclubs.ui.fragment.WCMeetingListFragment;
import com.mm.weclubs.ui.fragment.WCMineFragment;
import com.mm.weclubs.ui.fragment.WCMissionListFragment;
import com.mm.weclubs.ui.fragment.WCNotifyListFragment;
import com.mm.weclubs.ui.fragment.WCToolsFragment;

import dagger.Component;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(WCLoginActivity activity);
    void inject(WCRegisterActivity activity);
    void inject(WCNotifyDetailActivity activity);
    void inject(WCMissionDetailActivity activity);
    void inject(WCMeetingDetailActivity activity);

    void inject(WCMeetingManageDetailActivity activity);
    void inject(WCMeetingManageListActivity activity);
    void inject(WCMeetingParticipationDetailActivity activity);
    void inject(WCMissionManageListActivity activity);
    void inject(WCNotifyManageDetailActivity activity);
    void inject(WCNotifyManageListActivity activity);
    void inject(WCNotifyReceiveStatusActivity activity);

    void inject(WCDynamicFragment fragment);
    void inject(WCNotifyListFragment fragment);
    void inject(WCMissionListFragment fragment);
    void inject(WCMeetingListFragment fragment);
    void inject(WCIndexFragment fragment);
    void inject(WCMineFragment fragment);
    void inject(WCToolsFragment fragment);
}
