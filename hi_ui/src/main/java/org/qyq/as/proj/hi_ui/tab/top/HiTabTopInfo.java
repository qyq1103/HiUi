package org.qyq.as.proj.hi_ui.tab.top;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

import java.util.EnumMap;

/**
 * @Author: Net Spirit
 * @Time: 2023/5/29 21:18
 * @FixAuthor:
 * @FixTime:
 * @Desc:
 */
public class HiTabTopInfo<Color> {
    public enum TabType {
        //bitmap图标
        BITMAP,
        //文字
        TEXT
    }

    public Class<? extends Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;
    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;

    public HiTabTopInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabType.BITMAP;
    }

    public HiTabTopInfo(String name, Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.TEXT;
    }

}
