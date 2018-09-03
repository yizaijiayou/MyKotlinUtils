package com.scy.kotlinutils.utils

import android.util.Log

/**
 * 项 目 名: MyKotlinUtils
 * 创 建 人: 艺仔加油
 * 创建时间: 2018/5/7 18:26
 * 本类描述:
 */
object L {
    val DEBUG = true
    val TAG = "L"

    fun out(msg: Any) {
        if (DEBUG) println(msg)
    }

    fun d(msg: String) {
        if (DEBUG) Log.d(TAG, msg)
    }

    fun i(msg: String) {
        if (DEBUG) Log.i(TAG, msg)
    }

    fun v(msg: String) {
        if (DEBUG) Log.v(TAG, msg)
    }

    fun w(msg: String) {
        if (DEBUG) Log.w(TAG, msg)
    }

    fun e(msg: String) {
        if (DEBUG) Log.e(TAG, msg)
    }

    /**
     * 带tag的函数
     * @param tag  tag标识
     * @param msg  显示内容
     */
    fun d(tag: String, msg: String) {
        if (DEBUG) Log.d(tag, msg)
    }

    fun i(tag: String, msg: String) {
        if (DEBUG) Log.i(tag, msg)
    }

    fun v(tag: String, msg: String) {
        if (DEBUG) Log.v(tag, msg)
    }

    fun w(tag: String, msg: String) {
        if (DEBUG) Log.w(tag, msg)
    }

    fun e(tag: String, msg: String) {
        if (DEBUG) Log.e(tag, msg)
    }
}