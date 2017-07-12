package com.mm.weclubs.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.network.pojo.WCHotClubListInfo;
import com.mm.weclubs.data.network.pojo.WCIndexClubListInfo;
import com.mm.weclubs.data.network.pojo.WCStudentInfo;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import xyz.zpayh.adapter.BaseAdapter;
import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.TextCallback;
import xyz.zpayh.adapter.ViewCallback;

/**
 * 文 件 名: WCClubsListAdapter
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/10 21:52
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public abstract class WCClubsListAdapter extends BaseAdapter<WCIndexClubListInfo>{

    private final int STUDENT_IMG_IDS[] = {
            R.id.iv_student_1, R.id.iv_student_2, R.id.iv_student_3,
            R.id.iv_student_4, R.id.iv_student_5, R.id.iv_student_6
    };

    private final int HOT_CLUBS_VIEW_IDS[] = {
            R.id.ll_item_hot_clubs1, R.id.ll_item_hot_clubs2, R.id.ll_item_hot_clubs3,
            R.id.ll_item_hot_clubs4, R.id.ll_item_hot_clubs5
    };

    private final int HOT_CLUBS_LOGO_IDS[] = {
            R.id.img_club_logo1, R.id.img_club_logo2, R.id.img_club_logo3,
            R.id.img_club_logo4, R.id.img_club_logo5
    };

    private final int HOT_CLUBS_NAME_IDS[] = {
            R.id.tv_club_name1, R.id.tv_club_name2, R.id.tv_club_name3,
            R.id.tv_club_name4, R.id.tv_club_name5
    };

    private final List<WCHotClubListInfo> mHotClubs;

    public WCClubsListAdapter() {
        mHotClubs = new ArrayList<>();
    }

    @Override
    public int getLayoutRes(int index) {
        return R.layout.view_index_club_item;
    }

    @Override
    public void convert(BaseViewHolder holder, final WCIndexClubListInfo data, int index) {
        holder.setText(R.id.tv_club_name, data.getClub_name())
                .setText(R.id.tv_club_slogan, data.getSlogan())
                .setView(R.id.img_club_logo, new ViewCallback<RoundImageView>() {
                    @Override
                    public void callback(@NonNull RoundImageView view) {
                        view.setRectAdius(SizeUtils.dp2px(44));
                        ImageLoaderHelper.getInstance(view.getContext())
                                .loadImage(view, data.getAvatar_url());
                    }
                })
                .setText(R.id.tv_club_tag, new TextCallback() {
                    @Override
                    public void callback(@NonNull TextView textView) {
                        textView.setText(WCConstantsUtil.getClubLevelStr(data.getLevel()));
                        switch (data.getLevel()) {
                            case 0:
                                textView.setBackground(textView.getResources().getDrawable(R.drawable.club_level_tag_school2));
                                break;
                            case 1:
                                textView.setBackground(textView.getResources().getDrawable(R.drawable.club_level_tag_school2));
                                break;
                            case 2:
                                textView.setBackground(textView.getResources().getDrawable(R.drawable.club_level_tag_interest2));
                                break;
                            case 3:
                                textView.setBackground(textView.getResources().getDrawable(R.drawable.club_level_tag_class2));
                                break;
                            case 4:
                                textView.setBackground(textView.getResources().getDrawable(R.drawable.club_level_tag_freedom2));
                                break;
                        }
                    }
                });

        List<WCStudentInfo> studentInfos = data.getStudent();
        final int studentNumber = studentInfos == null ? 0 : studentInfos.size();
        for (int i = 0; i < STUDENT_IMG_IDS.length; i++) {
            if (i < studentNumber){
                //显示
                final WCStudentInfo info = studentInfos.get(i);
                holder.setView(STUDENT_IMG_IDS[i], new ViewCallback<RoundImageView>() {
                    @Override
                    public void callback(@NonNull RoundImageView view) {
                        view.setVisibility(View.VISIBLE);
                        ImageLoaderHelper.getInstance(view.getContext())
                                .loadImage(view,info.getAvatar_url());
                    }
                });
            } else {
                //隐藏
                holder.setVisibility(STUDENT_IMG_IDS[i], View.GONE);
            }
        }
        holder.setVisibility(R.id.iv_more_student, studentNumber > 6 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void convertHead(BaseViewHolder holder, int headLayout, int index) {
        super.convertHead(holder, headLayout, index);

        if (headLayout == R.layout.item_index_head){
            final int studentNumber = mHotClubs.size();
            for (int i = 0; i < HOT_CLUBS_VIEW_IDS.length; i++) {
                if (i < studentNumber){
                    //显示
                    final WCHotClubListInfo info = mHotClubs.get(i);
                    holder.setVisibility(HOT_CLUBS_VIEW_IDS[i], View.VISIBLE)
                    .setView(HOT_CLUBS_LOGO_IDS[i], new ViewCallback<RoundImageView>() {
                        @Override
                        public void callback(@NonNull RoundImageView view) {
                            ImageLoaderHelper.getInstance(view.getContext())
                                    .loadImage(view,info.getAvatar_url());
                        }
                    })
                    .setText(HOT_CLUBS_NAME_IDS[i],info.getClub_name());
                } else {
                    //隐藏
                    holder.setVisibility(HOT_CLUBS_VIEW_IDS[i], View.GONE);
                }
            }
        }
    }

    public void setHotClubs(List<WCHotClubListInfo> hotClubs){
        mHotClubs.clear();
        if (hotClubs != null && !hotClubs.isEmpty()) {
            mHotClubs.addAll(hotClubs);
        }
        notifyItemChanged(0);
    }
}
