package org.qyq.as.proj.hi_ui.tab.top;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import org.qyq.as.proj.hi_library.util.HiDisplayUtil;
import org.qyq.as.proj.hi_ui.tab.common.IHiTabLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: Net Spirit
 * @Time: 2023/5/29 21:50
 * @FixAuthor:
 * @FixTime:
 * @Desc: 顶部导航TabTop容器控件
 */
public class HiTabTopLayout extends HorizontalScrollView implements IHiTabLayout<HiTabTop, HiTabTopInfo<?>> {
    private List<OnTabSelectedListener<HiTabTopInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    private HiTabTopInfo<?> selectedInfo;
    private List<HiTabTopInfo<?>> infoList;

    private int tabWidth;

    public HiTabTopLayout(Context context) {
        this(context, null);
    }

    public HiTabTopLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiTabTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setVerticalScrollBarEnabled(false);
    }

    @Override
    public HiTabTop findTab(@NonNull HiTabTopInfo<?> info) {
        ViewGroup ll = getRootLayout(false);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof HiTabTop) {
                HiTabTop tab = (HiTabTop) child;
                return tab;
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<HiTabTopInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull HiTabTopInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void inflateInfo(@NonNull List<HiTabTopInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        LinearLayout linearLayout = getRootLayout(true);
        selectedInfo = null;
        //清除之前添加的HiTabBottom listener. Tips:Java foreach remove 问题
        Iterator<OnTabSelectedListener<HiTabTopInfo<?>>> iterator = tabSelectedChangeListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof HiTabTopInfo) {
                iterator.remove();
            }
        }
        for (int i = 0; i < infoList.size(); i++) {
            HiTabTopInfo<?> info = infoList.get(i);
            HiTabTop tab = new HiTabTop(getContext());
            tabSelectedChangeListeners.add(tab);
            tab.setHiTabInfo(info);
            linearLayout.addView(tab);
            tab.setOnClickListener(v -> {
                onSelected(info);
            });
        }
    }

    private LinearLayout getRootLayout(boolean clear) {
        LinearLayout rootView = (LinearLayout) getChildAt(0);
        if (rootView == null) {
            rootView = new LinearLayout(getContext());
            rootView.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            addView(rootView, params);
        } else if (clear) {
            rootView.removeAllViews();
        }
        return rootView;
    }

    private void onSelected(@NonNull HiTabTopInfo<?> nextInfo) {
        for (OnTabSelectedListener<HiTabTopInfo<?>> listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
        autoScroll(nextInfo);
    }

    private void autoScroll(HiTabTopInfo<?> nextInfo) {
        HiTabTop tabTop = findTab(nextInfo);
        if (tabTop == null) {
            return;
        }
        int index = infoList.indexOf(nextInfo);
        int[] loc = new int[2];
        //获取点击的控件在屏幕的位置
        tabTop.getLocationInWindow(loc);
        int scrollWith;
        if (tabWidth == 0) {
            tabWidth = tabTop.getWidth();
        }
        //判读点击了屏幕的左侧还是右侧
        if ((loc[0] + tabWidth / 2) > HiDisplayUtil.getDisplayWidthInPx(getContext()) / 2) {
            scrollWith = rangeScrollWidth(index, 2);
        } else {
            scrollWith = rangeScrollWidth(index, -2);
        }
        scrollTo(getScrollX() + scrollWith, 0);

    }

    /**
     * 获取可滚动的范围
     *
     * @param index 从第几个开始
     * @param range 向前向后的范围
     * @return 可滚动的范围
     */
    private int rangeScrollWidth(int index, int range) {
        int scrollWith = 0;
        for (int i = 0; i < Math.abs(range); i++) {
            int next;
            if (range < 0) {
                next = range + i + index;
            } else {
                next = range - i + index;
            }
            if (next >= 0 && next < infoList.size()) {
                if (range < 0) {
                    scrollWith -= scrollWith(next, false);
                } else {
                    scrollWith += scrollWith(next, true);
                }
            }
        }
        return scrollWith;
    }

    /**
     * 指定位置的控件可滚动的距离
     *
     * @param index   指定位置的控件
     * @param toRight 是否点击了屏幕右侧
     * @return 可滚动的距离
     */
    private int scrollWith(int index, boolean toRight) {
        HiTabTop target = findTab(infoList.get(index));
        if (target == null) {
            return 0;
        }
        Rect rect = new Rect();
        target.getLocalVisibleRect(rect);
        if (toRight) {
            //点击屏幕的右侧
            if (rect.right > tabWidth) {
                //right坐标大于控件的宽度时，说明完全没有展示
                return tabWidth;
            } else {
                //部分展示，减去已展示的宽度
                return tabWidth - rect.right;
            }
        } else {
            if (rect.left <= -tabWidth) {
                //left坐标小于等于控件的宽度，说明完全没有显示
                return tabWidth;
            } else if (rect.left > 0) {
                //显示部分
                return rect.left;
            } else {
                return 0;
            }
        }
    }
}
