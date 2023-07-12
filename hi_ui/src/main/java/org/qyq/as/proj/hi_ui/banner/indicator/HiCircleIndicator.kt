package org.qyq.`as`.proj.hi_ui.banner.indicator

import android.content.Context
import android.media.Image
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import org.qyq.`as`.proj.hi_library.util.HiDisplayUtil
import org.qyq.`as`.proj.hi_ui.R

/**
 *
 * @Author: Net Spirit
 * @Time: 2023/7/4 22:01
 * @FixAuthor:
 * @FixTime:
 * @Desc: kotlin圆形指示器
 *
 */
class HiCircleIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), HiIndicator<FrameLayout> {
    companion object {
        private const val VWC = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    /**
     * 正常状态下的指示点
     */
    @DrawableRes
    private val mPointNormal = R.drawable.shape_point_normal

    /**
     * 选中状态下的指示点
     */
    private val mPointSelected = R.drawable.shape_point_select

    /**
     * 指示点左右内边距
     */
    private var mPointLeftRightPadding = 0

    /**
     * 指示点上下内边距
     */
    private var mPointTopBottomPadding = 0

    init {
        mPointLeftRightPadding = HiDisplayUtil.dp2px(5f, resources)
        mPointTopBottomPadding = HiDisplayUtil.dp2px(15f, resources)
    }

    override fun get(): FrameLayout {
        return this
    }

    override fun onInflate(count: Int) {
        removeAllViews()
        if (count <= 0) {
            return
        }
        val groupView = LinearLayout(context)
        groupView.orientation = LinearLayout.HORIZONTAL
        var imageView: ImageView
        val imageViewParams = LinearLayout.LayoutParams(VWC, VWC)
        imageViewParams.gravity = Gravity.CENTER_VERTICAL
        imageViewParams.setMargins(
            mPointLeftRightPadding,
            mPointTopBottomPadding,
            mPointLeftRightPadding,
            mPointTopBottomPadding
        )
        for (i in 0 until count) {
            imageView = ImageView(context)
            if (i == 0) {
                imageView.setImageResource(mPointSelected)
            } else {
                imageView.setImageResource(mPointNormal)
            }
            groupView.addView(imageView)
        }
        val groupParams = LayoutParams(VWC, VWC)
        groupParams.gravity = Gravity.CENTER or Gravity.BOTTOM
        addView(groupView, groupParams)
    }

    override fun onPointChange(current: Int, count: Int) {
        val viewGroup = getChildAt(0) as ViewGroup
        for (i in 0 until viewGroup.childCount) {
            val imageView = viewGroup.getChildAt(i) as ImageView
            if (i == current) {
                imageView.setImageResource(mPointSelected)
            } else {
                imageView.setImageResource(mPointNormal)
            }
            imageView.requestLayout()
        }
    }
}