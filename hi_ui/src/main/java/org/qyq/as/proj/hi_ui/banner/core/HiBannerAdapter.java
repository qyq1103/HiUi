package org.qyq.as.proj.hi_ui.banner.core;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import org.qyq.as.proj.hi_ui.banner.HiBanner;

import java.util.List;

/**
 * @Author: Net Spirit
 * @Time: 2023/6/19 21:58
 * @FixAuthor:
 * @FixTime:
 * @Desc: HiViewPaper的适配器，微页面填充数据
 */
public class HiBannerAdapter extends PagerAdapter {
    private Context context;
    private SparseArray<HiBannerViewHolder> mCacheViews = new SparseArray<HiBannerViewHolder>();
    private IHiBanner.OnBannerClickListener mBannerClickListener;
    private IBindAdapter mBindAdapter;
    private List<? extends HiBannerMo> models;
    /**
     * 是否开启自动轮播
     */
    private boolean mAutoPlay = true;
    /**
     * 非自动轮播状态下是否可以循环
     */
    private boolean mLoop = false;

    private int mLayoutResId = -1;

    public HiBannerAdapter(Context context) {
        this.context = context;
    }

    public void setBannerData(@NonNull List<? extends HiBannerMo> models) {
        this.models = models;
        //初始化数据
        initCacheView();
        notifyDataSetChanged();
    }

    public void setOnBannerClickListener(IHiBanner.OnBannerClickListener bannerClickListener) {
        this.mBannerClickListener = bannerClickListener;
    }

    public void setBindAdapter(IBindAdapter bindAdapter) {
        this.mBindAdapter = bindAdapter;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
    }

    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    public void setLayoutResId(int layoutResId) {
        this.mLayoutResId = layoutResId;
    }


    @Override
    public int getCount() {
        //无限轮播关键点
        return mAutoPlay ? Integer.MAX_VALUE : (mLoop ? Integer.MAX_VALUE : getRealCount());
    }

    /**
     * 获取Banner页面数量
     *
     * @return banner数据的长度
     */
    public int getRealCount() {
        return models == null ? 0 : models.size();
    }


    /**
     * 获取初次展示的item位置
     *
     * @return 初次展示的item的位置
     */
    public int getFirstItem() {
        return Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % getRealCount();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position;
        if (getRealCount() > 0) {
            realPosition = position % getRealCount();
        }
        HiBannerViewHolder viewHolder = mCacheViews.get(realPosition);
        if (container.equals(viewHolder.rootView.getParent())) {
            container.removeView(viewHolder.rootView);
        }
        //数据绑定
        onBind(viewHolder, models.get(realPosition), realPosition);
        if (viewHolder.rootView.getParent() != null) {
            ((ViewGroup) viewHolder.rootView.getParent()).removeView(viewHolder.rootView);
        }
        container.addView(viewHolder.rootView);
        return viewHolder.rootView;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //让item每次都刷新
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    protected void onBind(@NonNull HiBannerViewHolder viewHolder, @NonNull HiBannerMo bannerMo, int position) {
        viewHolder.rootView.setOnClickListener(v -> {
            if (mBannerClickListener != null) {
                mBannerClickListener.onBannerClick(viewHolder, bannerMo, position);
            }
        });

        if (mBindAdapter != null) {
            mBindAdapter.onBind(viewHolder, bannerMo, position);
        }
    }

    private void initCacheView() {
        mCacheViews = new SparseArray<>();
        for (int i = 0; i < models.size(); i++) {
            HiBannerViewHolder viewHolder = new HiBannerViewHolder(createView(LayoutInflater.from(context), null));
            mCacheViews.put(i, viewHolder);
        }
    }

    private View createView(LayoutInflater inflater, ViewGroup parent) {
        if (mLayoutResId == -1) {
            throw new IllegalArgumentException("you must be set setLayoutResId first");
        }
        return inflater.inflate(mLayoutResId, parent, false);
    }

    public static class HiBannerViewHolder {
        private SparseArray<View> viewSparseArray;
        View rootView;

        public HiBannerViewHolder(View rootView) {
            this.rootView = rootView;
        }

        public View getRootView() {
            return rootView;
        }

        public <V extends View> V findViewById(int id) {
            if (!(rootView instanceof ViewGroup)) {
                return (V) rootView;
            }
            if (this.viewSparseArray == null) {
                this.viewSparseArray = new SparseArray<>();
            }
            V child = (V) viewSparseArray.get(id);
            if (child == null) {
                child = rootView.findViewById(id);
                this.viewSparseArray.put(id, child);
            }
            return child;
        }
    }
}
