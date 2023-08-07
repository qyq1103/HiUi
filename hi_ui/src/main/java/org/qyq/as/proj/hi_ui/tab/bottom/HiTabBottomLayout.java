package org.qyq.as.proj.hi_ui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.qyq.as.proj.hi_library.util.HiDisplayUtil;
import org.qyq.as.proj.hi_library.util.HiViewUtil;
import org.qyq.as.proj.hi_ui.R;
import org.qyq.as.proj.hi_ui.tab.common.IHiTabLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: Net Spirit
 * @Time: 2023/5/27 22:17
 * @FixAuthor:
 * @FixTime:
 * @Desc: tabBottom 容器组件
 */
public class HiTabBottomLayout extends FrameLayout implements IHiTabLayout<HiTabBottom, HiTabBottomInfo<?>> {
    /**
     * 所有监听集合
     */
    private List<OnTabSelectedListener<HiTabBottomInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    /**
     * 被选中的tabBottomInfo
     */
    private HiTabBottomInfo<?> selectedInfo;
    /**
     * 底部的透明度，默认不透明
     */
    private float bottomAlpha = 1f;
    /**
     * TabBottom高度
     */
    private static float tabBottomHeight = 50f;
    /**
     * TabBottom的头部线条高度
     */
    private float bottomLineHeight = 0.5f;
    /**
     * TabBottom的头部线条颜色
     */
    private String bottomLineColor = "#DFE0E1";

    private List<HiTabBottomInfo<?>> infoList;

    private static final String TAG_TAB_BOTTOM = "TAH_TAB_BOTTOM";

    public HiTabBottomLayout(@NonNull Context context) {
        super(context, null);
    }

    public HiTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public HiTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public HiTabBottom findTab(@NonNull HiTabBottomInfo<?> info) {
        ViewGroup fl = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < fl.getChildCount(); i++) {
            View child = fl.getChildAt(i);
            if (child instanceof HiTabBottom) {
                HiTabBottom tab = (HiTabBottom) child;
                if (tab.getTabInfo() == info) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<HiTabBottomInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull HiTabBottomInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void inflateInfo(@NonNull List<HiTabBottomInfo<?>> infoList) {
        //数据为空直接返回
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        //移除之前已经添加的View，防止重复添加
        //i>0是不能将中间的内容区移除，0是用户添加的内容区
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }
        selectedInfo = null;
        //添加背景色
        addBackground();
        //清除之前添加的HiTabBottom listener. Tips:Java foreach remove 问题
        Iterator<OnTabSelectedListener<HiTabBottomInfo<?>>> iterator = tabSelectedChangeListeners.iterator();
        //不使用for(i=0)是因为又进行遍历有进行删会报错，可换成迭代器或者for(i = tabSelectedChangeListeners.size()-1) 从后往前进行删
        while (iterator.hasNext()) {
            if (iterator.next() instanceof HiTabBottom) {
                iterator.remove();
            }
        }
        int height = HiDisplayUtil.dp2px(tabBottomHeight, getResources());
        FrameLayout fl = new FrameLayout(getContext());
        fl.setTag(TAG_TAB_BOTTOM);
        //获取每一个tabBottom的宽度
        int width = HiDisplayUtil.getDisplayWidthInPx(getContext()) / infoList.size();
        for (int i = 0; i < infoList.size(); i++) {
            HiTabBottomInfo<?> info = infoList.get(i);
            //Tips:为何不用LinearLayout:当动态改变child大小后Gravity.Bottom会失效
            LayoutParams params = new LayoutParams(width, height);
            params.gravity = Gravity.BOTTOM;
            //设置左边距，使得依次向右排列
            params.leftMargin = i * width;

            HiTabBottom tabBottom = new HiTabBottom(getContext());
            tabSelectedChangeListeners.add(tabBottom);
            tabBottom.setHiTabInfo(info);
            fl.addView(tabBottom, params);
            tabBottom.setOnClickListener(v -> onSelected(info));
        }
        LayoutParams flParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        flParams.gravity = Gravity.BOTTOM;
        addBottomLine();
        addView(fl, flParams);
        fixContentView();
    }

    public void setTabAlpha(float alpha) {
        this.bottomAlpha = alpha;
    }

    public static void setTabHeight(float height) {
        HiTabBottomLayout.tabBottomHeight = height;
    }

    public void setBottomLineHeight(float bottomLineHeight) {
        this.bottomLineHeight = bottomLineHeight;
    }

    public void setBottomLineColor(String bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    public static void clipBottomPadding(ViewGroup targetView) {
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, HiDisplayUtil.dp2px(tabBottomHeight));
            targetView.setClipToPadding(false);
        }
    }

    public void resizeHiTabBottomLayout() {
        int width = HiDisplayUtil.getDisplayWidthInPx(getContext()) / infoList.size();
        ViewGroup frameLayout = (ViewGroup) getChildAt(getChildCount() - 1);
        int childCount = frameLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View button = frameLayout.getChildAt(i);
            FrameLayout.LayoutParams params = (LayoutParams) button.getLayoutParams();
            params.width = width;
            params.leftMargin = i * width;
            button.setLayoutParams(params);
        }
    }


    private void onSelected(@NonNull HiTabBottomInfo<?> nextInfo) {
        for (OnTabSelectedListener<HiTabBottomInfo<?>> listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
    }

    private void addBackground() {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.hi_bottom_layout_bg, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, HiDisplayUtil.dp2px(tabBottomHeight, getResources()));
        params.gravity = Gravity.BOTTOM;
        addView(view, params);
        //设置透明度
        view.setAlpha(bottomAlpha);
    }

    /**
     * 添加线
     */
    private void addBottomLine() {
        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor));

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, HiDisplayUtil.dp2px(bottomLineHeight, getResources()));
        params.gravity = Gravity.BOTTOM;
        params.bottomMargin = HiDisplayUtil.dp2px(tabBottomHeight - bottomLineHeight, getResources());
        addView(bottomLine, params);
        bottomLine.setAlpha(bottomAlpha);
    }

    /**
     * 修复内容区域的底部padding
     */
    private void fixContentView() {
        //单节点的直接return
        if (!((getChildAt(0)) instanceof ViewGroup)) {
            return;
        }
        ViewGroup rootView = (ViewGroup) getChildAt(0);
        ViewGroup targetView = HiViewUtil.findTypeView(rootView, RecyclerView.class);
        if (targetView == null) {
            targetView = HiViewUtil.findTypeView(rootView, ScrollView.class);
        }
        if (targetView == null) {
            targetView = HiViewUtil.findTypeView(rootView, AbsListView.class);
        }
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, HiDisplayUtil.dp2px(tabBottomHeight, getResources()));
            //让底部padding可以绘制
            targetView.setClipToPadding(false);
        }
    }
}
