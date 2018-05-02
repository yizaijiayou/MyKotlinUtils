package com.scy.kotlinutils

import android.Manifest
import android.os.Bundle
import com.scy.kotlinutils.utils.base.BaseActivity
import com.scy.kotlinutils.utils.base.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    var ss = arrayOf("1", "2", "3")
    var i = 0
    override val getContentLayoutId: Int = R.layout.activity_main

    override fun init(savedInstanceState: Bundle?) {
        textView.setOnClickListener {
            requestPermission(arrayOf(Manifest.permission.CAMERA), object : PermissionListener {
                override fun onGranted() {
                toast("授权成功")
                }

                override fun onFature(perssion: String) {
                    toast("授权失败--->$perssion")
                }
            })
        }
    }

    override fun doWork() {
    }

}
