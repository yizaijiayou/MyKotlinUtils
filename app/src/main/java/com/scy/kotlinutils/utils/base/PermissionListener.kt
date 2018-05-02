package com.scy.kotlinutils.utils.base

/**
 * 项 目 名: MyKotlinUtils
 * 创 建 人: 艺仔加油
 * 创建时间: 2018/5/2 10:19
 * 本类描述:
 */
interface PermissionListener {
    val PERMISSION: Int get() = 10000

    fun onGranted()

    fun onFature(perssion:String)
}