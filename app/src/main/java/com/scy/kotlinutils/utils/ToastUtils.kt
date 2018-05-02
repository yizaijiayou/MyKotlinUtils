package com.scy.kotlinutils.utils

import android.content.Context
import android.widget.Toast

/**
 * 项 目 名: MyKotlinUtils
 * 创 建 人: 艺仔加油
 * 创建时间: 2018/5/2 10:05
 * 本类描述:
 */
object ToastUtils {
    lateinit var toast: Toast
    fun init(context: Context) {
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
    }

    fun show(msg: String) {
        if (toast != null) {
            toast.setText(msg)
            toast.show()
        }
    }
}
