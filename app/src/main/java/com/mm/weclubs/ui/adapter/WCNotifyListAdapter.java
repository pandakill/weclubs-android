package com.mm.weclubs.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.network.pojo.WCNotifyListInfo;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.widget.RoundImageView;

import xyz.zpayh.adapter.BaseAdapter;
import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.TextCallback;
import xyz.zpayh.adapter.ViewCallback;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/12 上午11:13
 * 描述:
 */

public class WCNotifyListAdapter extends BaseAdapter<WCNotifyListInfo> {

    @Override
    public int getLayoutRes(int index) {
        return R.layout.view_dynamic_notify_list_item;
    }

    @Override
    public void convert(BaseViewHolder holder, final WCNotifyListInfo data, final int index) {
        holder.setView(R.id.item_dynamic, new ViewCallback() {
            @Override
            public void callback(@NonNull View view) {
                if (index == 0){
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
                    params.topMargin = ConvertUtils.dp2px(16);
                    view.setLayoutParams(params);
                } else if (index == (getData().size() - 1)) {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
                    params.bottomMargin = ConvertUtils.dp2px(16);
                    view.setLayoutParams(params);
                }
            }
        }).setView(R.id.img_sponsor_logo, new ViewCallback<RoundImageView>() {
            @Override
            public void callback(@NonNull RoundImageView view) {
                view.setRectAdius(SizeUtils.dp2px(32));
                ImageLoaderHelper.getInstance(view.getContext())
                        .loadImage(view,data.getSponsor().getSponsor_avatar());
            }
        }).setText(R.id.tv_sponsor_name,data.getSponsor().getSponsor_name())
          .setText(R.id.tv_notify_content,data.getContent())
          .setText(R.id.tv_create_date,TimeUtils.millis2String(data.getCreate_date(), "MMMdd日"));

        if (data.getConfirm_receive() == 1){
            holder.setVisibility(R.id.icon_receive,View.VISIBLE);
            holder.setText(R.id.tv_btn_receive_text, new TextCallback() {
                @Override
                public void callback(@NonNull TextView textView) {
                    textView.setText("已确认收到");
                    textView.setTextColor(textView.getResources().getColor(R.color.themeColor));
                }
            }).setView(R.id.btn_receive, new ViewCallback() {
                @Override
                public void callback(@NonNull View view) {
                    view.setEnabled(false);
                }
            });
        }else{
            holder.setVisibility(R.id.icon_receive,View.GONE);
            holder.setText(R.id.tv_btn_receive_text, new TextCallback() {
                @Override
                public void callback(@NonNull TextView textView) {
                    textView.setText("确认收到");
                    textView.setTextColor(textView.getResources().getColor(R.color.themeColor));
                }
            }).setView(R.id.btn_receive, new ViewCallback() {
                @Override
                public void callback(@NonNull View view) {
                    view.setEnabled(true);
                }
            });
        }
    }

    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {
        holder.setClickable(R.id.item_dynamic,true)
                .setClickable(R.id.btn_receive,true);
    }
}
