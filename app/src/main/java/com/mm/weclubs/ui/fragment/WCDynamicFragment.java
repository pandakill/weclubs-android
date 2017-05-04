package com.mm.weclubs.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mm.weclubs.R;
import com.mm.weclubs.ui.adapter.WCMyDynamicAdapter;
import com.mm.weclubs.util.WCLog;

import java.util.ArrayList;

import me.fangx.haorefresh.HaoRecyclerView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/26 下午4:11
 * 描述:  首页动态的fragment
 */

public class WCDynamicFragment extends BaseLazyFragment {

    private SwipeRefreshLayout mRefreshLayout;
    private HaoRecyclerView mRecyclerView;

    @Override
    protected int getContentViewLayoutID() {
        log = new WCLog(WCDynamicFragment.class);
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initViewsAndEvents() {
        log.d("动态 initViewsAndEvents");
        mRefreshLayout = findViewById(R.id.swipeRefreshLayout, SwipeRefreshLayout.class);
        mRecyclerView = findViewById(R.id.recycler_view, HaoRecyclerView.class);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
        manager.canScrollVertically();
        mRecyclerView.setLayoutManager(manager);

        WCMyDynamicAdapter adapter = new WCMyDynamicAdapter(mContext);
        mRecyclerView.setAdapter(adapter);

        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(new Object());
        }

        adapter.setItems(list);
    }

    @Override
    protected void onFirstUserVisible() {
        log.d("动态 onFirstUserVisible");
        showToast("动态fragment创建");
    }

    @Override
    protected void onUserVisible() {
        log.d("动态 onUserVisible");
        showToast("动态fragment展示");
    }

    @Override
    protected void onUserInvisible() {

    }
}
