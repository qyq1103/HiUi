package org.qyq.`as`.proj.hiui.demo.tab

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import org.qyq.`as`.proj.hi_library.util.HiDisplayUtil
import org.qyq.`as`.proj.hi_ui.tab.bottom.HiTabBottomInfo
import org.qyq.`as`.proj.hi_ui.tab.bottom.HiTabBottomLayout
import org.qyq.`as`.proj.hiui.R

class HiTabBottomDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_tab_bottom_demo)
        initTabBottom()
    }

    private fun initTabBottom() {
        val hiTabBottomLayout = findViewById<HiTabBottomLayout>(R.id.hi_tab_layout)
        hiTabBottomLayout.setTabAlpha(0.85f)
        val bottomInfoList: MutableList<HiTabBottomInfo<*>> = ArrayList()
        val homeInfo = HiTabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#FF656667",
            "#FFD44949"
        )
        val recommendInfo = HiTabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            getString(R.string.if_favorite),
            null,
            "#FF656667",
            "#FFD44949"
        )
        /*val categoryInfo = HiTabBottomInfo(
            "分类",
            "fonts/iconfont.ttf",
            getString(R.string.if_category),
            null,
            "#FF656667",
            "#FFD44949"
        )*/
        //将资源文件解析成bitmap
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.fire, null);
        val categoryInfo = HiTabBottomInfo<String>(
            "分类",
            bitmap,
            bitmap
        )

        val chatInfo = HiTabBottomInfo(
            "推荐",
            "fonts/iconfont.ttf",
            getString(R.string.if_recommend),
            null,
            "#FF656667",
            "#FFD44949"
        )
        val profileInfo = HiTabBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            getString(R.string.if_profile),
            null,
            "#FF656667",
            "#FFD44949"
        )
        bottomInfoList.add(homeInfo)
        bottomInfoList.add(recommendInfo)
        bottomInfoList.add(categoryInfo)
        bottomInfoList.add(chatInfo)
        bottomInfoList.add(profileInfo)

        hiTabBottomLayout.inflateInfo(bottomInfoList)

        hiTabBottomLayout.defaultSelected(homeInfo)
        //改变某个tab的高度
        val tabBottom = hiTabBottomLayout.findTab(bottomInfoList[2])
        tabBottom?.apply {
            //不为空的情况下重新设置高度
            resetHeight(HiDisplayUtil.dp2px(66f, resources))
        }
        hiTabBottomLayout.addTabSelectedChangeListener { _, _, nextInfo ->
            Toast.makeText(this@HiTabBottomDemoActivity, nextInfo.name, Toast.LENGTH_SHORT).show()
        }
    }
}