package com.mm.weclubs.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mm.weclubs.R;
import com.mm.weclubs.util.ImageLoaderHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class BannerPageAdapter extends PagerAdapter {

    private final ArrayList<String> mPictureUris;

    private final LinkedList<View> mCacheViews;

    public BannerPageAdapter() {
        mPictureUris = new ArrayList<>();
        mCacheViews = new LinkedList<>();
    }

    public void setPicture(List<String> pictureUris){
        mPictureUris.clear();
        if (pictureUris != null){
            mPictureUris.addAll(pictureUris);
        }
        notifyDataSetChanged();
    }

    public ArrayList<String> getPictureUris() {
        return mPictureUris;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView;
        View view;
        if (mCacheViews.isEmpty()){
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_picture,container,false);
            PageViewHolder holder = new PageViewHolder(view);
            holder.position = position;
            imageView = holder.mImageView;
        }else{
            view = mCacheViews.remove();
            PageViewHolder holder = (PageViewHolder) view.getTag();
            holder.position = position;
            imageView = holder.mImageView;
        }

        final String pictureUri = mPictureUris.get(position);

        ImageLoaderHelper.getInstance(container.getContext())
                .loadImage(imageView,pictureUri);

        //Glide.with(container.getContext()).load(pictureUri).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
        mCacheViews.add(view);
    }

    @Override
    public int getCount() {
        return mPictureUris.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private class PageViewHolder{
        ImageView mImageView;
        int position;
        PageViewHolder(View itemView) {
            mImageView = (ImageView) itemView.findViewById(R.id.iv_picture);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickListener != null){
                        mOnClickListener.onClick(v);
                    }
                }
            });
            itemView.setTag(this);
        }
    }
    private View.OnClickListener mOnClickListener;
    public void setOnClickListener(View.OnClickListener onClickListener){
        mOnClickListener = onClickListener;
    }
}
