package com.mm.weclubs.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

import com.blankj.utilcode.utils.ScreenUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.network.bean.WCToolBean;
import com.mm.weclubs.ui.activity.manage.WCMeetingManageListActivity;
import com.mm.weclubs.ui.activity.manage.WCMissionManageListActivity;
import com.mm.weclubs.ui.activity.manage.WCNotifyManageListActivity;
import com.mm.weclubs.ui.adapter.WCManageItemAdapter;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.util.StatusBarUtil;
import com.mm.weclubs.util.WCLog;

import java.util.ArrayList;

import javax.inject.Inject;

import xyz.zpayh.adapter.OnItemClickListener;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/26 下午4:11
 * 描述:  首页动态的fragment
 */

public class WCToolsFragment extends BaseLazyFragment {
    public static final String TAG = "WCToolsFragment";
    public static WCToolsFragment newInstance(){
        WCToolsFragment fragment = new WCToolsFragment();
        return fragment;
    }

    private ImageView mImgToolBanner;
    private RecyclerView mGvManageList;
    private RecyclerView mGvInformationList;

    private ArrayList<WCToolBean> mManageTools;
    private ArrayList<WCToolBean> mInformationTools;

    private WCManageItemAdapter mManageItemAdapter;
    private WCManageItemAdapter mInformationItemAdapter;

    @Inject
    ImageLoaderHelper mImageLoaderHelper;

    @Override
    protected int getContentViewLayoutID() {
        log = new WCLog(WCToolsFragment.class);
        return R.layout.fragment_tools;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {
        initTools();
        initView();
        afterView();
    }

    private void initView() {

        getActivityComponent().inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //修复状态栏高度
            StatusBarUtil.fixStatusHeight(findView(R.id.fake_status_bar));
        }

        mImgToolBanner = findViewById(R.id.img_tool_banner, ImageView.class);
        mGvManageList = findViewById(R.id.gv_manage_list, RecyclerView.class);
        mGvInformationList = findViewById(R.id.gv_information_list, RecyclerView.class);

        GridLayoutManager managerListManager = new GridLayoutManager(mContext, 4);
        mGvManageList.setLayoutManager(managerListManager);

        GridLayoutManager informationListManager = new GridLayoutManager(mContext, 4);
        mGvInformationList.setLayoutManager(informationListManager);
    }

    private void initTools() {
        mManageTools = new ArrayList<>();
        WCToolBean notify = new WCToolBean();
        notify.setDrawable(getResources().getDrawable(R.mipmap.tool_ic_notice));
        notify.setTitle("通知管理");
        mManageTools.add(notify);
        WCToolBean meeting = new WCToolBean();
        meeting.setDrawable(getResources().getDrawable(R.mipmap.tool_ic_meeting));
        meeting.setTitle("会议管理");
        mManageTools.add(meeting);
        WCToolBean mission = new WCToolBean();
        mission.setDrawable(getResources().getDrawable(R.mipmap.tool_ic_task));
        mission.setTitle("任务管理");
        mManageTools.add(mission);

        mInformationTools = new ArrayList<>();
        WCToolBean setting = new WCToolBean();
        setting.setDrawable(getResources().getDrawable(R.mipmap.tool_ic_setting));
        setting.setTitle("组织设置");
        mInformationTools.add(setting);
        WCToolBean club = new WCToolBean();
        club.setDrawable(getResources().getDrawable(R.mipmap.tool_ic_setup));
        club.setTitle("创建组织");
        mInformationTools.add(club);
    }

    private void afterView() {
        mImageLoaderHelper.loadImage(mImgToolBanner,
                "http://img0.pconline.com.cn/pconline/download/iosdl/1611/A4.jpg");

        LayoutParams layoutParams = (LayoutParams) mImgToolBanner.getLayoutParams();
        layoutParams.height = WCConstantsUtil.getProportionHeight(ScreenUtils.getScreenWidth(), 750, 320);
        mImgToolBanner.setLayoutParams(layoutParams);

        mManageItemAdapter = new WCManageItemAdapter();
        mInformationItemAdapter = new WCManageItemAdapter();

        mGvManageList.setAdapter(mManageItemAdapter);
        mGvInformationList.setAdapter(mInformationItemAdapter);

        mManageItemAdapter.setData(mManageTools);
        mInformationItemAdapter.setData(mInformationTools);

        mManageItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                WCToolBean toolBean = mManageItemAdapter.getData(adapterPosition);
                if (toolBean != null) {
                    switch (toolBean.getTitle()) {
                        case "通知管理":
                            showIntent(WCNotifyManageListActivity.class);
                            break;
                        case "会议管理":
                            showIntent(WCMeetingManageListActivity.class);
                            break;
                        case "任务管理":
                            showIntent(WCMissionManageListActivity.class);
                            break;
                    }
                }
            }
        });

        mInformationItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                WCToolBean toolBean = mInformationItemAdapter.getData(adapterPosition);
                if (toolBean != null) {
                    switch (toolBean.getTitle()) {
                        case "组织设置":
                            showToast("组织设置");
                            break;
                        case "创建组织":
                            showToast("创建组织");
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onFirstUserVisible() {
        log.d("工具 onFirstUserVisible");
        showToast("工具fragment创建");
    }

    @Override
    protected void onUserVisible() {
        log.d("工具 onUserVisible");
        showToast("工具fragment展示");
    }

    @Override
    protected void onUserInvisible() {

    }
}