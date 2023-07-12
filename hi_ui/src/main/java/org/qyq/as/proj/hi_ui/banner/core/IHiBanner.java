package org.qyq.as.proj.hi_ui.banner.core;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import org.qyq.as.proj.hi_ui.banner.indicator.HiIndicator;

import java.util.List;

/**
 * @Author: Net Spirit
 * @Time: 2023/6/19 21:45
 * @FixAuthor:
 * @FixTime:
 * @Desc:
 */
public interface IHiBanner {
    void setBannerData(@LayoutRes int layoutResId, @NonNull List<? extends HiBannerMo> models);

    void setBannerData(@NonNull List<? extends HiBannerMo> models);

    void setHiIndicator(HiIndicator<?> hiIndicator);

    void setAutoPlay(boolean autoPlay);

    void setLoop(boolean loop);

    void setIntervalTime(int intervalTime);

    void setBindAdapter(IBindAdapter bindAdapter);

    void setOnPagerChangeListener(ViewPager.OnPageChangeListener onPagerChangeListener);

    void setOnBannerClickListener(OnBannerClickListener onBannerClickListener);

    void setScrollDuration(int duration);

    interface OnBannerClickListener {
        void onBannerClick(@NonNull HiBannerAdapter.HiBannerViewHolder viewHolder, @NonNull HiBannerMo bannerMo, int position);
    }
}
