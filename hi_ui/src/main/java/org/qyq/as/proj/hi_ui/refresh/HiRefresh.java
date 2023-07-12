package org.qyq.as.proj.hi_ui.refresh;

/**
 * @Author: Net Spirit
 * @Time: 2023/6/11 21:04
 * @FixAuthor:
 * @FixTime:
 * @Desc:
 */
public interface HiRefresh {
    /**
     * 刷新时是否禁止滚动
     *
     * @param disableRefreshScroll true:禁止滚动 false:可以滚动
     */
    void setDisableRefreshScroll(boolean disableRefreshScroll);

    /**
     * 刷新完成
     */
    void refreshFinished();

    /**
     * 设置下拉刷新的监听器
     *
     * @param listener 刷新监听器
     */
    void setRefreshListener(HiRefreshListener listener);

    /**
     * 设置下拉刷新的视图
     *
     * @param hiOverView 下拉刷新的视图
     */
    void setRefreshOverView(HiOverView hiOverView);


    interface HiRefreshListener {
        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 是否启用刷新
         *
         * @return true:启用 false: 不启用
         */
        boolean enableRefresh();
    }
}
