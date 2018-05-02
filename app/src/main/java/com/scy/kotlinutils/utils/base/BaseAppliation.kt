package com.scy.kotlinutils.utils.base

import android.app.Application
import com.scy.kotlinutils.utils.ToastUtils

/**
 * 项 目 名: MyKotlinUtils
 * 创 建 人: 艺仔加油
 * 创建时间: 2018/5/2 10:09
 * 本类描述:
 */
class BaseAppliation:Application(){
    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)
    }
}
