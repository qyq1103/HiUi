package org.qyq.as.proj.hi_ui.banner.indicator;

import android.view.View;

/**
 * @Author: Net Spirit
 * @Time: 2023/6/19 21:49
 * @FixAuthor:
 * @FixTime:
 * @Desc:
 */
public interface HiIndicator<T extends View> {
    T get();

    /**
     * 初始化Indicator
     *
     * @param count 幻灯片数量
     */
    void onInflate(int count);

    /**
     * 幻灯片切换回调
     *
     * @param current 切换到的幻灯片位置
     * @param count   幻灯片数量
     */
    void onPointChange(int current, int count);
}
