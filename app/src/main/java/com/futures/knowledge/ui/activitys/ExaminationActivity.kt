package com.futures.knowledge.ui.activitys

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.futures.knowledge.R
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_examination.*

class ExaminationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examination)
        ImmersionBar.with(this).statusBarColor(R.color.colorBlue).fitsSystemWindows(true).init()
        initView()
    }

    private fun initView() {
        iv_back.setOnClickListener(View.OnClickListener { finish() })
    }


}