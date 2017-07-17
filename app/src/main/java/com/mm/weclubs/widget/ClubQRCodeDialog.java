package com.mm.weclubs.widget;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.mm.weclubs.R;


public class ClubQRCodeDialog extends DialogFragment {

    public static final String TAG = "ClubQRCodeDialog";

    private View.OnLongClickListener mOnLongClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_club_qrcode,container,false);

        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
            }
        });

        view.findViewById(R.id.iv_qrcode).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongClickListener != null){
                    return mOnLongClickListener.onLongClick(v);
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int width = ScreenUtils.getScreenWidth();

        int maxWidth = SizeUtils.dp2px(311);

        if (maxWidth >= width){
            return;
        }

        Dialog dialog = getDialog();
        if (dialog == null){
            return;
        }
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(maxWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mOnLongClickListener = onLongClickListener;
    }
}
