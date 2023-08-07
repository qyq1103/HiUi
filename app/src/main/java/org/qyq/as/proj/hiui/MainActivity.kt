package org.qyq.`as`.proj.hiui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.qyq.`as`.proj.hi_ui.tab.bottom.HiTabBottom
import org.qyq.`as`.proj.hi_ui.tab.bottom.HiTabBottomInfo
import org.qyq.`as`.proj.hiui.demo.tab.HiTabBottomDemoActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_tab_bottom -> {
                startActivity(Intent(this, HiTabBottomDemoActivity::class.java))
            }
        }
    }

}