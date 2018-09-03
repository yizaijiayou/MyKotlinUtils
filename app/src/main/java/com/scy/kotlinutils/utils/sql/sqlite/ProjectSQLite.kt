package com.scy.kotlinutils.utils.sql.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * 项 目 名: MyKotlinUtils
 * 创 建 人: 艺仔加油
 * 创建时间: 2018/5/7 16:06
 * 本类描述:
 */
class ProjectSQLite(context: Context,name:String,version:Int) : SQLiteOpenHelper(context,name,null,version) {
    companion object {
        val SQLNAME = "ProtectSQLite"
        val SQLVERSION = 1

        val TABLE_1 = "table1"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE IF NOT EXISTS " + TABLE_1 + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR," +
                "value VARCHAR)"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table id exists $TABLE_1")
        onCreate(db)
    }
}
