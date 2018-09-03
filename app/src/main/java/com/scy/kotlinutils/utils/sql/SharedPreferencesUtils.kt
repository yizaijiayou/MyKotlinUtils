package com.scy.kotlinutils.utils.sql

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.scy.kotlinutils.R

/**
 * 项 目 名: MyKotlinUtils
 * 创 建 人: 艺仔加油
 * 创建时间: 2018/5/7 15:25
 * 本类描述:
 */
class SharedPreferencesUtils constructor(context: Context){
    var sharedPreferences: SharedPreferences
    var editor: SharedPreferences.Editor

     init{
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    //------------------------------------------------------------------put------------------------------------------------------------------------
    fun putString(key: String, value: String) {
        editor.putString(key, value)
    }

    fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
    }

    fun putInt(key: String, value: Int) {
        editor.putInt(key, value)
    }

    fun putFloat(key: String, value: Float) {
        editor.putFloat(key, value)
    }

    fun putLong(key: String, value: Long) {
        editor.putLong(key, value)
    }

    fun putStirngSet(key: String, value: Set<String>) {
        editor.putStringSet(key, value)
    }

    //------------------------------------------------------------------get------------------------------------------------------------------------
    fun getString(key: String): String {
        return sharedPreferences.getString(key, "")
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun getFloat(key: String): Float {
        return sharedPreferences.getFloat(key, 0f)
    }

    fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

    fun getStirngSet(key: String): Set<String>? {
        return sharedPreferences.getStringSet(key, null)
    }

    //------------------------------------------------------------------------------------------------------------------------------------------
    fun apply() {
        editor.apply()
    }

    fun commit() {
        editor.commit()
    }
}
