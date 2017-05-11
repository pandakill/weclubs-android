package com.mm.weclubs.ui.adapter.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.EmptyUtils;
import com.mm.weclubs.util.ImageLoaderHelper;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/16 下午2:58
 * 描述: 万能的适配器
 */
public abstract class WCBaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {

    protected Context mContext;
    protected ArrayList<T> mItems;
//    protected OnItemClickListener mItemOnClickListener = null;
    protected OnClickViewListener mClickViewListener = null;

    public WCBaseRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public interface OnItemClickListener {
        void onClickItem(int position);
    }

    public interface OnClickViewListener {
        void onClick(View view, int position);
    }

//    public void setOnClickItemListener(OnItemClickListener listener) {
//        mItemOnClickListener = listener;
//    }

    public void setOnClickViewListener(OnClickViewListener listener) {
        mClickViewListener = listener;
    }

    public void addItems(ArrayList<T> items) {
        if (EmptyUtils.isEmpty(items)) {
            return;
        }

        if (EmptyUtils.isEmpty(mItems)) {
            setItems(items);
            return;
        }

        for (T item : items) {
            mItems.add(item);
        }

        notifyDataSetChanged();
    }

    /**
     * 在数组最前面插入
     *
     * @param item  实体
     */
    public void addItemOnStart(T item) {
        if (EmptyUtils.isEmpty(mItems)) {
            ArrayList<T> items = new ArrayList<>();
            items.add(item);

            setItems(items);
        } else {
            mItems.add(0, item);
            notifyItemInserted(0);
        }
    }

    /**
     * 在数组最后面插入一个
     *
     * @param item  实体
     */
    public void addItemOnEnd(T item) {
        if (EmptyUtils.isEmpty(mItems)) {
            ArrayList<T> items = new ArrayList<>();
            items.add(item);

            setItems(items);
        } else {
            mItems.add(getItemCount(), item);
            notifyItemInserted(mItems.size()-1);
        }
    }

    public void setItems(ArrayList<T> items) {
//        if (!LibCommonUtils.isEmpty(mItems)) {
//            mItems.clear();
//        }
        mItems = items;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (EmptyUtils.isEmpty(mItems)) {
            return null;
        } else {
            return mItems.get(position);
        }
    }

    public ArrayList<T> getItems() {
        return mItems;
    }

    @Override
    public WCBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(getItemLayoutID(viewType), parent, false);
        return new WCBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            onBindDataToView((WCBaseViewHolder) holder, position);
//            ((MLYBaseViewHolder) holder).setViewOnClick(R.id.item_view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (EmptyUtils.isEmpty(mItems)) {
            return 0;
        }
        return mItems.size();
    }

    /**
     * 在这里配置每个viewType对应的布局文件id
     *
     * @param viewType 自定义的viewType
     * @return 返回该viewType对应的布局文件id
     */
    public abstract int getItemLayoutID(int viewType);

    /**
     * 绑定数据到holder里面
     *
     * @param holder   {@link WCBaseViewHolder}
     * @param position itemView的位置
     */
    protected abstract void onBindDataToView(WCBaseViewHolder holder, int position);


    /**
     * 单个itemView的点击事件,可以重写该方法实现操作
     *
     * @param position itemView的位置
     */
//    protected void onClickItem(int position) {
//        if (mItemOnClickListener != null) {
//            mItemOnClickListener.onClickItem(position);
//        }
//    }

    /**
     * 单个itemView里面的某些view的点击事件,可以重写该方法实现操作
     *
     * @param position itemView的位置
     * @param view     响应事件的具体view
     */
    protected void onClickItemView(int position, View view) {
    }

    public class WCBaseViewHolder extends RecyclerView.ViewHolder {

        private final SparseArray<View> mViews;
        private View mItemView;

        public WCBaseViewHolder(View itemView) {
            super(itemView);
            this.mViews = new SparseArray<>();
            this.mItemView = itemView;
//            mItemView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onClickItem(getAdapterPosition());
//                }
//            });
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mItemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        /**
         * 设置view的文字信息
         * 如果{@link #getView(int)} 结果不是{@link TextView} 或者{@link Button}的话,则会抛出异常
         *
         * @param viewId view的id
         * @param text   设置的文字信息
         */
        public void setText(int viewId, String text) {
            if (getView(viewId) == null) {
                throw new RuntimeException("找不到ID为viewId的视图,请检查后重新执行.");
            }
            if (getView(viewId) instanceof TextView) {
                ((TextView) getView(viewId)).setText(text);
            } else if (getView(viewId) instanceof Button) {
                ((Button) getView(viewId)).setText(text);
            } else {
                throw new RuntimeException("view无法执行setText方法.");
            }
        }

        /**
         * 设置view的文字信息
         * 如果{@link #getView(int)} 结果不是{@link TextView} 或者{@link Button}的话,则会抛出异常
         *
         * @param viewId view的id
         * @param text   设置的文字信息
         */
        public void setText(int viewId, CharSequence text) {
            if (getView(viewId) == null) {
                throw new RuntimeException("找不到ID为viewId的视图,请检查后重新执行.");
            }
            if (getView(viewId) instanceof TextView) {
                ((TextView) getView(viewId)).setText(text);
            } else if (getView(viewId) instanceof Button) {
                ((Button) getView(viewId)).setText(text);
            } else {
                throw new RuntimeException("view无法执行setText方法.");
            }
        }

        /**
         * 设置图片,该方法是从本地资源文件中设置
         * 如果{@link #getView(int)} 结果不是{@link ImageView}的话,则会抛出异常
         *
         * @param viewId view的id
         * @param resId  图片资源id
         */
        public void setImage(int viewId, int resId) {
            if (getView(viewId) == null) {
                throw new RuntimeException("找不到ID为viewId的视图,请检查后重新执行.");
            }
            if (getView(viewId) instanceof ImageView) {
                ((ImageView) getView(viewId)).setImageResource(resId);
            } else {
                throw new RuntimeException("view无法执行setImageResource方法.");
            }
        }

        /**
         * 设置图片,该方法是从url地址中获取图片进行显示
         * 如果{@link #getView(int)} 结果不是{@link ImageView}的话,则会抛出异常
         *
         * @param viewId view的id
         * @param url    图片资源id
         */
        public void setImage(int viewId, String url) {
            if (getView(viewId) == null) {
                throw new RuntimeException("找不到ID为viewId的视图,请检查后重新执行.");
            }
            if (getView(viewId) instanceof ImageView) {
                ImageLoaderHelper.getInstance(mContext).loadImage(getView(viewId), url);

            } else {
                throw new RuntimeException("view无法执行setImageResource方法.");
            }
        }

        public void setImage(int viewId, String url, int defaultDrawable) {
            if (getView(viewId) == null) {
                throw new RuntimeException("找不到ID为viewId的视图,请检查后重新执行.");
            }
            if (getView(viewId) instanceof ImageView) {
                // TODO: 2017/5/5 需要设置默认图片
                ImageLoaderHelper.getInstance(mContext).loadImage(getView(viewId), url);

            } else {
                throw new RuntimeException("view无法执行setImageResource方法.");
            }
        }

        /**
         * 设置图
         * 如果{@link #getView(int)} 结果不是{@link View}的话,则会抛出异常
         *
         * @param viewId view的id
         * @param resId  图片资源id
         */
        public void setViewBg(int viewId, int resId) {
            if (getView(viewId) == null) {
                throw new RuntimeException("找不到ID为viewId的视图,请检查后重新执行.");
            }
            if (getView(viewId) instanceof View) {
                getView(viewId).setBackgroundResource(resId);
            } else {
                throw new RuntimeException("view无法执行setBackgroundResource方法.");
            }
        }

        /**
         * 设置图片背景颜色
         * 如果{@link #getView(int)} 结果不是{@link View}的话,则会抛出异常
         *
         * @param viewId view的id
         * @param color  颜色
         */
        public void setViewBgColor(int viewId, int color) {
            if (getView(viewId) == null) {
                throw new RuntimeException("找不到ID为viewId的视图,请检查后重新执行.");
            }
            if (getView(viewId) instanceof View) {
                getView(viewId).setBackgroundColor(color);
            } else {
                throw new RuntimeException("view无法执行setBackgroundColor方法.");
            }
        }

        /**
         * 设置图片,该方法是从url地址中获取图片进行显示
         * 如果{@link #getView(int)} 结果不是{@link View}的话,则会抛出异常
         *
         * @param viewId     view的id
         * @param left       图片资源id
         * @param top        图片资源Drawable
         * @param right      图片资源Drawable
         * @param bottom     图片资源Drawable
         * @param padding_dp 设置图片和text之间的间距（已经转好才能传进来）
         */
        public void setDrawables(int viewId, Drawable left, Drawable top, Drawable right, Drawable bottom, int padding_dp) {
            if (getView(viewId) == null) {
                throw new RuntimeException("找不到ID为viewId的视图,请检查后重新执行.");
            }
            if (getView(viewId) instanceof TextView) {
                ((TextView) getView(viewId)).setCompoundDrawables(left, top, right, bottom);//设置TextView的drawableleft
                ((TextView) getView(viewId)).setCompoundDrawablePadding(padding_dp);//设置图片和text之间的间距

            } else {
                throw new RuntimeException("view无法执行setCompoundDrawables方法.");
            }
        }

        /**
         * 设置view的文字颜色
         * 如果{@link #getView(int)} 结果不是{@link TextView} 或者{@link Button}的话,则会抛出异常
         *
         * @param viewId view的id
         * @param resId  颜色资源id
         */
        public void setTextColor(int viewId, int resId) {
            if (getView(viewId) == null) {
                throw new RuntimeException("找不到ID为viewId的视图,请检查后重新执行.");
            }
            if (getView(viewId) instanceof TextView) {
                ((TextView) getView(viewId)).setTextColor(resId);
            } else if (getView(viewId) instanceof Button) {
                ((Button) getView(viewId)).setTextColor(resId);
            } else {
                throw new RuntimeException("view无法执行setTextColor方法.");
            }
        }

        /**
         * 设置为选中状态
         *
         * @param viewId   视图id
         * @param selected 是否选中
         */
        public void setSelected(int viewId, boolean selected) {
            if (getView(viewId) == null) {
                throw new RuntimeException("找不到ID为viewId的视图,请检查后重新执行.");
            }
            getView(viewId).setSelected(selected);
        }

        /**
         * 设置view的可见方式
         *
         * @param viewId  view的id
         * @param visible 参考{@link View#VISIBLE}{@link View#INVISIBLE}{@link View#GONE}
         */
        public void setViewVisible(int viewId, int visible) {
            if (getView(viewId) == null) {
                throw new RuntimeException("找不到ID为viewId的视图,请检查后重新执行.");
            }
            if (visible < View.VISIBLE || visible > View.GONE) {
                throw new RuntimeException("visible参数不合法.");
            }
            getView(viewId).setVisibility(visible);
        }

        /**
         * 为单个itemView里面的子view设置监听器
         *
         * @param viewId view的id
         */
        public void setViewOnClick(int viewId) {
//            if (null == getView(viewId) && viewId != R.id.item_view) {
//                throw new RuntimeException("找不到ID为viewId的视图,请检查后重新执行.");
//            }
//            if (null == getView(viewId) && viewId == R.id.item_view) {
//                return;
//            }
            getView(viewId).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemView(getAdapterPosition(), v);
                }
            });
        }

        /**
         * 通过id查找view
         *
         * @param viewId 视图id
         * @return 该视图
         */
//        public View findViewById(int viewId) {
//            if (null == getView(viewId) && viewId != R.id.item_view) {
//                throw new RuntimeException("找不到ID为viewId的视图,请检查后重新执行.");
//            }
//            return getView(viewId);
//        }
    }
}
