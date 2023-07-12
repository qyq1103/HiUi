package org.qyq.as.proj.hi_ui.banner.core;

import android.content.Context;
import android.widget.Scroller;

/**
 * @Author: Net Spirit
 * @Time: 2023/7/4 21:37
 * @FixAuthor:
 * @FixTime:
 * @Desc:
 */
public class HiBannerScroller extends Scroller {
    /**
     * 值越大，滑动越慢
     */
    private int mDuration = 1000;

    public HiBannerScroller(Context context, int duration) {
        super(context);
        mDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
