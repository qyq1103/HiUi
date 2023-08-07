package org.qyq.as.proj.hi_ui.tab.bottom;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/**
 * @Author: Net Spirit
 * @Time: 2023/5/27 21:26
 * @FixAuthor:
 * @FixTime:
 * @Desc: tabBottom属性类
 */
public class HiTabBottomInfo<Color> {
    public enum TabType { //图标类型,bitmap,icon
        BITMAP,
        ICON
    }

    /**
     * 持有fragment的class,动态创建fragment实例
     */
    public Class<? extends Fragment> fragment;
    public String name;
    /**
     * 默认bitmap图标
     */
    public Bitmap defaultBitmap;
    /**
     * 选中bitmap图标
     */
    public Bitmap selectedBitmap;
    /**
     * 字体图标
     */
    public String iconFont;
    /**
     * tip : 在java代码中直接设置iconFont字符串无效。需要定义在string.xml
     */

    public String defaultIconName;
    public String selectedIconName;
    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;

    public HiTabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabType.BITMAP;
    }

    public HiTabBottomInfo(String name, String iconFont, String defaultIconName, String selectedIconName, Color defaultColor, Color tintColor) {
        this.name = name;
        this.iconFont = iconFont;
        this.defaultIconName = defaultIconName;
        this.selectedIconName = selectedIconName;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.ICON;
    }
}
