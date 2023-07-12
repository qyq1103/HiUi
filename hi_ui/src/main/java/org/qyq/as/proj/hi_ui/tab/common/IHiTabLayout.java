package org.qyq.as.proj.hi_ui.tab.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * @Author: Net Spirit
 * @Time: 2023/5/27 20:59
 * @FixAuthor:
 * @FixTime:
 * @Desc: TabLayout 外部实现接口
 */
public interface IHiTabLayout<Tab extends ViewGroup, D> {
    /**
     * 根据数据查找tab
     *
     * @param info 数据
     * @return tab(top / bottom)
     */
    Tab findTab(@NonNull D info);

    /**
     * 监听器添加
     *
     * @param listener 监听实现
     */
    void addTabSelectedChangeListener(OnTabSelectedListener<D> listener);

    /**
     * 默认选中
     *
     * @param defaultInfo 默认选中数据
     */
    void defaultSelected(@NonNull D defaultInfo);

    /**
     * 初始化数据
     * @param infoList 数据列表
     */
    void inflateInfo(@NonNull List<D> infoList);

    /**
     * 选中tab监听接口
     *
     * @param <D> 数据
     */
    interface OnTabSelectedListener<D> {
        /**
         * 选中tab 监听方法
         *
         * @param index    选中的下标
         * @param prevInfo 上一个数据
         * @param nextInfo 下一个数据
         */
        void onTabSelectedChange(int index, @NonNull D prevInfo, @NonNull D nextInfo);
    }
}
