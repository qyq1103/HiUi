package org.qyq.as.proj.hi_ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.qyq.as.proj.hi_library.util.HiDisplayUtil;


/**
 * @Author: Net Spirit
 * @Time: 2023/6/11 21:14
 * @FixAuthor:
 * @FixTime:
 * @Desc: 下拉刷新视图
 */
public abstract class HiOverView extends FrameLayout {
    public enum HiRefreshState {
        /**
         * 初始状态
         */
        STATE_INIT,
        /**
         * header展示的状态
         */
        STATE_VISIBLE,
        /**
         * 刷新中的状态
         */
        STATE_REFRESH,
        /**
         * 超出可刷新距离的状态
         */
        STATE_OVER,
        /**
         * 超出刷新位置松开手后的状态
         */
        STATE_OVER_RELEASE
    }

    /**
     * 触发下拉刷新需要的最小高度
     */
    public int mPullRefreshHeight;

    /**
     * 最小阻尼
     */
    public float minDamp = 1.6f;

    /**
     * 最大阻尼
     */
    public float maxDamp = 2.2f;

    /**
     * 状态，默认为初始状态
     */
    protected HiRefreshState mState = HiRefreshState.STATE_INIT;

    public HiOverView(@NonNull Context context) {
        super(context);
        preInit();
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        preInit();
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preInit();
    }

    /**
     * 初始化
     */
    public abstract void init();

    /**
     * 超过Overlay,释放就会加载
     */
    public abstract void onOver();

    /**
     * 开始刷新
     */
    public abstract void onRefresh();

    /**
     * 加载完成
     */
    public abstract void onFinish();

    /**
     * 获取状态
     *
     * @return 状态
     */
    public HiRefreshState getState() {
        return mState;
    }

    /**
     * 设置状态
     *
     * @param state 状态
     */
    public void setState(HiRefreshState state) {
        this.mState = state;
    }

    protected void preInit() {
        mPullRefreshHeight = HiDisplayUtil.dp2px(66, getResources());
        init();
    }

    /**
     * @param scrollY           滚动的Y轴
     * @param pullRefreshHeight 下拉刷新的高度
     */
    protected abstract void onScroll(int scrollY, int pullRefreshHeight);

    /**
     * 显示Overlay
     */
    protected abstract void onVisible();


}
