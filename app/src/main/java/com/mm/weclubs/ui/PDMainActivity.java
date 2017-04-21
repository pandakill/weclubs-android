package com.mm.weclubs.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mm.weclubs.R;
import com.mm.weclubs.rxbus.RxBus;
import com.mm.weclubs.rxbus.events.LoginSuccessEvent;
import com.mm.weclubs.ui.adapter.FragmentAdapter;
import com.mm.weclubs.ui.fragment.PDMineFragment;
import com.mm.weclubs.ui.fragment.PDMyDailyFragment;
import com.mm.weclubs.ui.fragment.PDSquareFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import rx.Subscription;


public class PDMainActivity extends AppCompatActivity {

    FrameLayout mContent;
    BottomNavigationView mBottomNavigationView;
    FloatingActionButton mFab;
    ViewPager mViewPager;

    PDMineFragment mMineFragment;
    PDMyDailyFragment mMyDailyFragment;
    PDSquareFragment mSquareFragment;

    Subscription mRxSubscription = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mMineFragment = new PDMineFragment();
        mMyDailyFragment = new PDMyDailyFragment();
        mSquareFragment = new PDSquareFragment();

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        mViewPager = (ViewPager) findViewById(R.id.content_viewpager);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(mMyDailyFragment);
        fragments.add(mSquareFragment);
        fragments.add(mMineFragment);
        adapter.setFragments(fragments);

        mViewPager.setAdapter(adapter);

        mFab.setOnClickListener(view -> Toast.makeText(this, "Replace with your own action", Toast.LENGTH_LONG).show());

        mBottomNavigationView.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.isChecked()) {
                    return false;
                }

                switch (item.getItemId()) {
                    case R.id.item1:
                        mFab.show();
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.item2:
                        mFab.hide();
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.item3:
                        mFab.hide();
                        mViewPager.setCurrentItem(2);
                        break;
                }

                Toast.makeText(PDMainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mBottomNavigationView.getMenu().getItem(mViewPager.getCurrentItem()).setChecked(false);
            }

            @Override
            public void onPageSelected(int position) {
                mBottomNavigationView.getMenu().getItem(mViewPager.getCurrentItem()).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mRxSubscription = RxBus.getDefault().toObservable(LoginSuccessEvent.class)
                .subscribe(loginSuccessEvent -> {
                    Toast.makeText(PDMainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    protected void onDestroy() {
        if (!mRxSubscription.isUnsubscribed()) {
            mRxSubscription.unsubscribe();
        }
        super.onDestroy();
    }
}
