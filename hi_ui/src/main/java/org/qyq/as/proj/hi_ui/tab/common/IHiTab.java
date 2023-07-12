package org.qyq.as.proj.hi_ui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * @Author: Net Spirit
 * @Time: 2023/5/27 20:59
 * @FixAuthor:
 * @FixTime:
 * @Desc: tab 外部实现接口
 */
public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D> {

    /**
     * 设置数据
     *
     * @param data 数据
     */
    void setHiTabInfo(@NonNull D data);

    /**
     * 动态修改某个item的大小
     * @param height 高度 （单位 px）
     */
    void resetHeight(@Px int height);
}
