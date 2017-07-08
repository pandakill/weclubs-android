package com.mm.weclubs.ui.adapter.manage;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.network.pojo.WCNotifyReceiveStatusInfo;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.widget.RoundImageView;

import xyz.zpayh.adapter.BaseAdapter;
import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.TextCallback;
import xyz.zpayh.adapter.ViewCallback;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/17 下午6:38
 * 描述:
 */

public class WCNotifyReceiveAdapter extends BaseAdapter<WCNotifyReceiveStatusInfo> {

    @Override
    public int getLayoutRes(int index) {
        return R.layout.view_meeting_paticipation_item;
    }

    @Override
    public void convert(BaseViewHolder holder, final WCNotifyReceiveStatusInfo data, int index) {
        // TODO: 2017/5/17
//        String department = getItem(position).getDepartment_name() + "  " + getItem(position).getJob_name();
        String department = "TODO";

        holder.setVisibility(R.id.tv_confirm_status, View.GONE)
                .setVisibility(R.id.tv_mark, View.GONE)
                .setView(R.id.img_student_logo, new ViewCallback<RoundImageView>() {
                    @Override
                    public void callback(@NonNull RoundImageView view) {
                        view.setRectAdius(SizeUtils.dp2px(40));
                        ImageLoaderHelper.getInstance(view.getContext())
                                .loadImage(view,data.getStudent_avatar());
                    }
                }).setText(R.id.tv_student_name, data.getStudent_name())
                .setText(R.id.tv_student_department, department)
                .setText(R.id.tv_create_date, TimeUtils.millis2String(data.getCreate_date(), "MMMdd日  HH:mm"))
                .setText(R.id.tv_sign_status, new TextCallback() {
                    @Override
                    public void callback(@NonNull TextView textView) {
                        textView.setText("已查看");
                        if (data.getIs_confirm() == 1){
                            Drawable receiveDrawable = textView.getResources().getDrawable(R.mipmap.sign_ic_sure);
                            receiveDrawable.setBounds(0, 0, receiveDrawable.getMinimumWidth(), receiveDrawable.getMinimumHeight());
                            textView.setCompoundDrawables(receiveDrawable, null, null, null);
                            textView.setTextColor(textView.getResources().getColor(R.color.colorCommonText));
                        }else{
                            Drawable receiveDrawable = textView.getResources().getDrawable(R.mipmap.sign_ic_uncertain);
                            receiveDrawable.setBounds(0, 0, receiveDrawable.getMinimumWidth(), receiveDrawable.getMinimumHeight());
                            textView.setCompoundDrawables(receiveDrawable, null, null, null);
                            textView.setTextColor(textView.getResources().getColor(R.color.colorCommonText_666));
                        }
                    }
                });
    }

    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {

    }
}
