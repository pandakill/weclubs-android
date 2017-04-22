package com.mm.weclubs.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.mm.weclubs.R;
import com.mm.weclubs.rxbus.RxBus;
import com.mm.weclubs.rxbus.events.LoginSuccessEvent;
import com.mm.weclubs.ui.fragment.PDMineFragment;
import com.mm.weclubs.ui.fragment.PDSquareFragment;
import com.mm.weclubs.widget.MLYFragmentTabHost;

import rx.Subscription;


public class PDMainActivity extends AppCompatActivity {

    private TabWidget mTabs;
    private MLYFragmentTabHost mTabHost;

    private FragmentManager mFragmentManager;

    Subscription mRxSubscription = null;

    // tabHost 图标
    private int mFragmentDrawables[] = {
            R.drawable.tab_host_index,
            R.drawable.tab_host_dynamic,
            R.drawable.tab_host_tools,
            R.drawable.tab_host_mine
    };
    // tabHost 标题
    private String mFragmentTags[] = {
            "首页", "动态", "工具", "我的"
    };

    // fragment
    private Class<?> mFragments[] = {
            PDMineFragment.class,
            PDSquareFragment.class,
            PDSquareFragment.class,
            PDMineFragment.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabHost = (MLYFragmentTabHost) findViewById(android.R.id.tabhost);
        mTabs = (TabWidget) findViewById(android.R.id.tabs);

        mFragmentManager = getSupportFragmentManager();
        initTabHost();

        mRxSubscription = RxBus.getDefault().toObservable(LoginSuccessEvent.class)
                .subscribe(loginSuccessEvent -> {
                    Toast.makeText(PDMainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                });
    }

    private void initTabHost() {
        mTabHost.setup(this, mFragmentManager, android.R.id.tabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);   // 去除分割线
        mTabHost.clearAllTabs();

        for (int i = 0; i < mFragmentTags.length; i++) {
            // 添加图片和文字
            TabSpec tabSpec = mTabHost.newTabSpec(mFragmentTags[i]).setIndicator(getImageView(i));
            // 设置对应的fragment
            mTabHost.addTab(tabSpec, mFragments[i], null);
            // 设置背景
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(android.R.color.white);
            mTabHost.getTabWidget().getChildAt(i).setTag(i);
            mTabHost.getTabWidget().getChildAt(i).setOnClickListener(v -> {
                switch ((Integer) v.getTag()) {
                    case 0:
                        clickTagHost((Integer) v.getTag(), "index");
                        break;
                    case 1:
                        clickTagHost((Integer) v.getTag(), "dynamic");
                        break;
                    case 2:
                        clickTagHost((Integer) v.getTag(), "tools");
                        break;
                    case 3:
                        clickTagHost((Integer) v.getTag(), "mine");
                        break;
                }

            });
        }
    }

    private void clickTagHost(int tag, String tagId) {
        mTabs.setCurrentTab(tag);
        mTabHost.onTabChanged(tagId);
    }

    // 获得图片资源
    private View getImageView(int index) {
        @SuppressLint("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.view_tab_indicator, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_iv_image);
        TextView textView = (TextView) view.findViewById(R.id.tab_tv_title);
        textView.setText(mFragmentTags[index]);
        imageView.setImageResource(mFragmentDrawables[index]);
        return view;
    }

    @Override
    protected void onDestroy() {
        if (!mRxSubscription.isUnsubscribed()) {
            mRxSubscription.unsubscribe();
        }
        super.onDestroy();
    }
}
