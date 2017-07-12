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

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.utils.ScreenUtils;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.AppDataManager;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.db.AppDbHelper;
import com.mm.weclubs.data.db.DbHelper;
import com.mm.weclubs.data.db.database.AppDatabase;
import com.mm.weclubs.data.network.ApiHelper;
import com.mm.weclubs.data.network.AppApiHelper;
import com.mm.weclubs.data.prefs.AppPreferencesHelper;
import com.mm.weclubs.data.prefs.PreferencesHelper;
import com.mm.weclubs.di.ApiInfo;
import com.mm.weclubs.di.AppUUid;
import com.mm.weclubs.di.ApplicationContext;
import com.mm.weclubs.di.ApplicationGlide;
import com.mm.weclubs.di.BaseUrl;
import com.mm.weclubs.di.DatabaseInfo;
import com.mm.weclubs.di.DeviceHeight;
import com.mm.weclubs.di.DeviceSize;
import com.mm.weclubs.di.DeviceWidth;
import com.mm.weclubs.di.PreferenceInfo;
import com.mm.weclubs.glide.okhttp3.GlideApp;
import com.mm.weclubs.glide.okhttp3.GlideRequests;
import com.mm.weclubs.util.MD5Util;
import com.socks.library.KLog;

import java.util.Locale;
import java.util.UUID;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    private final String mUUid;
    private final String mDeviceSize;

    private final int mWidth;
    private final int mHeight;

    private final GlideRequests mGlideRequests;

    public ApplicationModule(Application application) {
        mApplication = application;

        int sw = ScreenUtils.getScreenWidth();
        int sh = ScreenUtils.getScreenHeight();

        mDeviceSize = String.format(Locale.CHINA,"%d,%d", sw,sh);

        String uuid = sw + "-" + UUID.randomUUID().toString() + "-" + sh;

        KLog.d("initUUID: 原始uuid = " + uuid);

        uuid = MD5Util.md5(uuid);

        if (uuid != null){
            uuid = uuid.toLowerCase();
        }

        KLog.d("initUUID: 加密后的uuid = " + uuid);

        mUUid = uuid;
        mWidth = sw;
        mHeight = sh;

        mGlideRequests = GlideApp.with(application);
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @ApplicationGlide
    GlideRequests provideGlideRequests(){
        return mGlideRequests;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @DeviceWidth
    @Provides
    int provideWidth(){
        return mWidth;
    }

    @DeviceHeight
    @Provides
    int provideHeight(){
        return mHeight;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return "weClubs";
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return "";
    }

    @Provides
    @BaseUrl
    String provideBaseUrl(){
        return WCConfigConstants.HTTP_BASE_URL;
    }

    @Provides
    @DeviceSize
    String provideDeviceSize(){
        return mDeviceSize;
    }

    @Provides
    @AppUUid
    String provideAppUUid(PreferencesHelper preferencesHelper){
        String uuid = preferencesHelper.getUUid();
        if (TextUtils.isEmpty(uuid)){
            preferencesHelper.saveUUid(mUUid);
            return mUUid;
        }
        return uuid;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return "weClubs";
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@ApplicationContext Context context,@DatabaseInfo String databaseName){
        return Room.databaseBuilder(context,AppDatabase.class,databaseName).build();
    }
}
