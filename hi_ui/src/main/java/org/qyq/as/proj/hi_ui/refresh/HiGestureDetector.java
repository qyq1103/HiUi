package org.qyq.as.proj.hi_ui.refresh;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * @Author: Net Spirit
 * @Time: 2023/6/11 21:33
 * @FixAuthor:
 * @FixTime:
 * @Desc:
 */
public class HiGestureDetector implements GestureDetector.OnGestureListener {
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
