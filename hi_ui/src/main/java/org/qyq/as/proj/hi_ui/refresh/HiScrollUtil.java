package org.qyq.as.proj.hi_ui.refresh;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: Net Spirit
 * @Time: 2023/6/11 21:59
 * @FixAuthor:
 * @FixTime:
 * @Desc:
 */
public class HiScrollUtil {
    public static boolean childScrolled(@NonNull View child) {
        if (child instanceof AdapterView) {
            AdapterView adapterView = (AdapterView) child;
            if (adapterView.getFirstVisiblePosition() != 0
                    || adapterView.getFirstVisiblePosition() == 0
                    && adapterView.getChildAt(0) != null
                    && adapterView.getChildAt(0).getTop() < 0) {
                return true;
            }
        } else if (child.getScaleY() > 0) {
            return true;
        }
        if (child instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) child;
            View view = recyclerView.getChildAt(0);
            int firstPosition = recyclerView.getChildAdapterPosition(view);
            return firstPosition != 0 || view.getTop() != 0;
        }
        return false;
    }

    /**
     * 查找可以滚动的child
     *
     * @param viewGroup 查询的父级
     * @return 可以滚动的child
     */
    public static View findScrollableChild(@NonNull ViewGroup viewGroup) {
        View child = viewGroup.getChildAt(1);
        if (child instanceof RecyclerView || child instanceof AdapterView) {
            return child;
        }
        if (child instanceof ViewGroup) {
            View tempChild = ((ViewGroup) child).getChildAt(0);
            if (tempChild instanceof RecyclerView || tempChild instanceof AdapterView) {
                child = tempChild;
            }
        }
        return child;
    }
}
