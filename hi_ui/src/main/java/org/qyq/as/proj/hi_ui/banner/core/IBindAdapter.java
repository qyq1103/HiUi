package org.qyq.as.proj.hi_ui.banner.core;

/**
 * @Author: Net Spirit
 * @Time: 2023/6/19 21:56
 * @FixAuthor:
 * @FixTime:
 * @Desc: HiBanner的数据绑定接口，基于该接口实现数据的绑定和框架层解耦
 */
public interface IBindAdapter {
    void onBind(HiBannerAdapter.HiBannerViewHolder viewHolder, HiBannerMo mo, int position);
}
