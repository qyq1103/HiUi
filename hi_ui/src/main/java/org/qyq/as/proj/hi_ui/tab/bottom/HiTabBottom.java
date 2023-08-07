package org.qyq.as.proj.hi_ui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import org.qyq.as.proj.hi_ui.R;
import org.qyq.as.proj.hi_ui.tab.common.IHiTab;

/**
 * @Author: Net Spirit
 * @Time: 2023/5/27 21:41
 * @FixAuthor:
 * @FixTime:
 * @Desc: 单个 tabBottom 实现
 */
public class HiTabBottom extends RelativeLayout implements IHiTab<HiTabBottomInfo<?>> {
    private HiTabBottomInfo<?> tabInfo;
    private ImageView tabImageView;
    private TextView tabIconView;
    private TextView tabNameView;

    public HiTabBottom(Context context) {
        this(context, null);
    }

    public HiTabBottom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiTabBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    public void setHiTabInfo(@NonNull HiTabBottomInfo<?> data) {
        this.tabInfo = data;
        inflateInfo(false, true);
    }


    @Override
    public void resetHeight(int height) {
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = height;
        setLayoutParams(params);
        getTabNameView().setVisibility(GONE);
    }

    @Override
    public void onTabSelectedChange(int index, @NonNull HiTabBottomInfo<?> prevInfo, @NonNull HiTabBottomInfo<?> nextInfo) {
        //当前选中的和下一个选中的都不是tabInfo,或者重复选中自己则直接return
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
            return;
        }
        //被反选了，即点击了当前之外的tabBottom
        if (prevInfo == tabInfo) {
            inflateInfo(false, false);
        } else {
            inflateInfo(true, false);
        }
    }

    public HiTabBottomInfo<?> getTabInfo() {
        return tabInfo;
    }

    public ImageView getTabImageView() {
        return tabImageView;
    }

    public TextView getTabIconView() {
        return tabIconView;
    }

    public TextView getTabNameView() {
        return tabNameView;
    }

    /**
     * 初始化
     */
    private void init() {
        //加载视图到当前HiTabBottom
        LayoutInflater.from(getContext())
                .inflate(R.layout.hi_tab_bottom, this);

        tabImageView = findViewById(R.id.iv_image);
        tabIconView = findViewById(R.id.tv_icon);
        tabNameView = findViewById(R.id.tv_name);
    }


    /**
     * 重新加载数据
     *
     * @param selected 是否选中
     * @param init     是否需要初始化
     */
    private void inflateInfo(boolean selected, boolean init) {
        //判断tabInfo是否是IconFont的类型
        if (tabInfo.tabType == HiTabBottomInfo.TabType.ICON) {
            //判断是否需要初始化
            if (init) {
                //隐藏图片
                tabImageView.setVisibility(GONE);
                //显示tabIcon
                tabIconView.setVisibility(VISIBLE);
                //设置字体
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), tabInfo.iconFont);
                tabIconView.setTypeface(typeface);
                //bottom名字是否为空，不为空则设置
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabNameView.setText(tabInfo.name);
                }
            }
            //是否被选中
            if (selected) {
                //tabIcon 选中IconName为空的时候设置为默认的IconName
                tabIconView.setText(TextUtils.isEmpty(tabInfo.selectedIconName) ? tabInfo.defaultIconName : tabInfo.selectedIconName);
                //设置选中的颜色
                tabIconView.setTextColor(getTextColor(tabInfo.tintColor));
                tabNameView.setTextColor(getTextColor(tabInfo.tintColor));
            } else {
                //未选中设置为默认的IconFont
                tabIconView.setText(tabInfo.defaultIconName);
                //设置默认颜色
                tabIconView.setTextColor(getTextColor(tabInfo.defaultColor));
                tabNameView.setTextColor(getTextColor(tabInfo.defaultColor));
            }
        } else if (tabInfo.tabType == HiTabBottomInfo.TabType.BITMAP) {
            if (init) {
                //显示图片
                tabImageView.setVisibility(VISIBLE);
                //隐藏Icon
                tabIconView.setVisibility(GONE);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabNameView.setText(tabInfo.name);
                }
            }
            if (selected) {
                //设置为选中的bitmap
                tabImageView.setImageBitmap(tabInfo.selectedBitmap);
            } else {
                //设置为默认(未选中)的bitmap
                tabImageView.setImageBitmap(tabInfo.defaultBitmap);
            }
        }
    }

    /**
     * 将颜色值转成int 颜色值
     *
     * @param color 颜色值（字符串或int）
     * @return int color
     */
    @ColorInt
    private int getTextColor(Object color) {
        if (color instanceof String) {
            return Color.parseColor((String) color);
        }
        return (int) color;
    }
}
