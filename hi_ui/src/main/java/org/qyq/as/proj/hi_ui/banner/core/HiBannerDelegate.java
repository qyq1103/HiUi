package org.qyq.as.proj.hi_ui.banner.core;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import org.qyq.as.proj.hi_ui.R;
import org.qyq.as.proj.hi_ui.banner.HiBanner;
import org.qyq.as.proj.hi_ui.banner.indicator.HiCircleIndicator;
import org.qyq.as.proj.hi_ui.banner.indicator.HiIndicator;
import org.qyq.as.proj.hi_ui.tab.top.HiTabTop;

import java.time.OffsetDateTime;
import java.util.List;

import javax.crypto.spec.PSource;

/**
 * @Author: Net Spirit
 * @Time: 2023/7/4 21:19
 * @FixAuthor:
 * @FixTime:
 * @Desc: HiBanner的控制
 * 辅助HiBanner完成各种功能的控制
 * 将HiBanner的一些逻辑内聚在这，保证暴露给使用者的HiBanner干净整洁
 */
public class HiBannerDelegate implements IHiBanner, ViewPager.OnPageChangeListener {
    private Context mContext;
    private HiBanner mBanner;
    private HiBannerAdapter mAdapter;
    private HiIndicator<?> mIndicator;
    private boolean mAutoPlay;
    private boolean mLoop;
    private List<? extends HiBannerMo> mBannerMos;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mIntervalTime = 5000;
    private IHiBanner.OnBannerClickListener mOnBannerClickListener;
    private HiViewPager mViewPager;
    private int mScrollDuration = -1;


    public HiBannerDelegate(Context context, HiBanner hiBanner) {
        this.mContext = context;
        this.mBanner = hiBanner;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null && mAdapter.getRealCount() != 0) {
            mOnPageChangeListener.onPageScrolled(
                    position % mAdapter.getRealCount(),
                    positionOffset,
                    positionOffsetPixels
            );
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mAdapter.getRealCount() == 0) {
            return;
        }
        position = position % mAdapter.getRealCount();
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (mIndicator != null) {
            mIndicator.onPointChange(position, mAdapter.getRealCount());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends HiBannerMo> models) {
        mBannerMos = models;
        init(layoutResId);
    }

    @Override
    public void setBannerData(@NonNull List<? extends HiBannerMo> models) {
        setBannerData(R.layout.hi_banner_item_image, models);
    }

    @Override
    public void setHiIndicator(HiIndicator<?> indicator) {
        this.mIndicator = indicator;
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
        if (mAdapter != null) {
            mAdapter.setAutoPlay(mAutoPlay);
        }
        if (mViewPager != null) {
            mViewPager.setAutoPlay(mAutoPlay);
        }
    }

    @Override
    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        if (intervalTime > 0) {
            this.mIntervalTime = intervalTime;
        }
    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {
        mAdapter.setBindAdapter(bindAdapter);
    }

    @Override
    public void setOnPagerChangeListener(ViewPager.OnPageChangeListener onPagerChangeListener) {
        this.mOnPageChangeListener = onPagerChangeListener;
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.mOnBannerClickListener = onBannerClickListener;
    }

    @Override
    public void setScrollDuration(int duration) {
        this.mScrollDuration = duration;
        if (mViewPager != null && duration > 0) {
            mViewPager.setScrollDuration(duration);
        }
    }

    private void init(int layoutResId) {
        if (mAdapter == null) {
            mAdapter = new HiBannerAdapter(mContext);
        }
        if (mIndicator == null) {
            mIndicator = new HiCircleIndicator(mContext);
        }

        mIndicator.onInflate(mBannerMos.size());
        mAdapter.setLayoutResId(layoutResId);
        mAdapter.setBannerData(mBannerMos);
        mAdapter.setAutoPlay(mAutoPlay);
        mAdapter.setLoop(mLoop);
        mAdapter.setOnBannerClickListener(mOnBannerClickListener);

        mViewPager = new HiViewPager(mContext);
        mViewPager.setIntervalTime(mIntervalTime);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAutoPlay(mAutoPlay);
        mViewPager.setAdapter(mAdapter);

        if (mScrollDuration > 0) {
            mViewPager.setScrollDuration(mScrollDuration);
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        if ((mLoop || mAutoPlay) && mAdapter.getRealCount() != 0) {
            //无限轮播关键点，使第一张能反向滑动到最后一张，已达到无限轮播的效果
            int firstItem = mAdapter.getFirstItem();
            mViewPager.setCurrentItem(firstItem, false);
        }
        //清除所有的view
        mBanner.removeAllViews();
        mBanner.addView(mViewPager, layoutParams);
        mBanner.addView(mIndicator.get(), layoutParams);
    }
}
