package com.scy.kotlinutils.utils.sql.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.text.TextUtils
import com.scy.kotlinutils.utils.base.BaseAppliation

/**
 * 项 目 名: MyKotlinUtils
 * 创 建 人: 艺仔加油
 * 创建时间: 2018/5/7 16:35
 * 本类描述:
 */
class CacheData constructor() {
    companion object {
        val CACHEDATA = "cacheData"
        var cacheData: CacheData? = null

        val instance: CacheData
            get() {
                if (cacheData == null) cacheData = CacheData()
                return cacheData!!
            }
    }

    var sqlSQLiteDatabase: SQLiteDatabase

    init {
        val context = BaseAppliation.appContext
        val projectSQLite = ProjectSQLite(context!!, ProjectSQLite.SQLNAME, ProjectSQLite.SQLVERSION)
        sqlSQLiteDatabase = projectSQLite.readableDatabase
    }

//*******************上为初始化，下为操作******************************************************************************

    fun selectCount(name: String): Int {
        var count = 0
        val cursor = sqlSQLiteDatabase.rawQuery("select * from $CACHEDATA where name = '$name' order by _id desc", null)
        count = cursor.count
        return count
    }

    /**
     * 增
     */
    fun insertCacheData(name: String, value: String) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {
            throw NullPointerException("name 和 value 存在空值")
        }

        if (selectCount(name) > 0) {
            updateCacheData(name, value)
        } else {
            val contentValue = ContentValues()
            contentValue.put("name", name)
            contentValue.put("value", value)
            sqlSQLiteDatabase.insert(CACHEDATA, null, contentValue)
        }
    }

    /**
     * 删
     */
    fun deleteCacheData(name: String) {
        if (TextUtils.isEmpty(name)) throw NullPointerException("name 存在空值")

        sqlSQLiteDatabase.beginTransaction()
        sqlSQLiteDatabase.delete(CACHEDATA, "name = ?", arrayOf(name))
        sqlSQLiteDatabase.setTransactionSuccessful()
        sqlSQLiteDatabase.endTransaction()
    }

    /**
     * 查
     */
    fun selectCacheData(name: String): String {
        if (TextUtils.isEmpty(name)) throw NullPointerException("name 存在空值")

        var data = ""
        val cusor = sqlSQLiteDatabase.rawQuery("select * from $CACHEDATA where name = '$name' order by _id desc", null)
        if (cusor.count != 0) {
            cusor.moveToNext()
            data = cusor.getString(cusor.getColumnIndex("value"))
        }
        cusor.close()
        return data
    }

    /**
     * 改
     */
    fun updateCacheData(name: String, value: String) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) throw NullPointerException("name 和 value 存在空值")

        val contentValue = ContentValues()
        contentValue.put("name", name)
        contentValue.put("value", value)
        sqlSQLiteDatabase.update(CACHEDATA, contentValue, null, null)
    }
}