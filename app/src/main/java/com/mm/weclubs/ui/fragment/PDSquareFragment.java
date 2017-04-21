package com.mm.weclubs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mm.weclubs.R;


/**
 * 创建人: fangzanpan
 * 创建时间: 2017/2/15 下午2:26
 * 描述:
 */

public class PDSquareFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_square, null);
    }
}
