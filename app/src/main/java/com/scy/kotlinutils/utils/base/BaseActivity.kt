package com.scy.kotlinutils.utils.base

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.scy.dialogloading.LoadingDialog
import com.scy.kotlinutils.R
import com.scy.kotlinutils.utils.ToastUtils
import com.scy.kotlinutils.utils.sql.SharedPreferencesUtils
import com.scy.kotlinutils.utils.sql.sqlite.ProjectSQLite
import java.lang.StringBuilder
import java.util.ArrayList

/**
 * 项 目 名: MyKotlinTest
 * 创 建 人: 艺仔加油
 * 创建时间: 2018/5/2 9:44
 * 本类描述:
 */
abstract class BaseActivity : AppCompatActivity() {

    //获取layout文件
    abstract val getContentLayoutId: Int

    //初始化参数
    abstract fun init(savedInstanceState: Bundle?)

    //操作方法
    abstract fun doWork()

    lateinit var loadingDialog: LoadingDialog
    lateinit var sharedPreferences: SharedPreferencesUtils
    lateinit var sqLiteDatabase: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //竖屏显示
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        loadingDialog = LoadingDialog(this, ContextCompat.getColor(this, R.color.colorAccent))
        sharedPreferences = SharedPreferencesUtils(this)
        val projectSQLite = ProjectSQLite(this,ProjectSQLite.SQLNAME,ProjectSQLite.SQLVERSION)
        sqLiteDatabase = projectSQLite.readableDatabase

        setContentView(getContentLayoutId)
        init(savedInstanceState)
        doWork()
    }

    fun toast(msg: String) {
        ToastUtils.show(msg)
    }

    fun showLoadingDialog() {
        if (!loadingDialog.isShowing) loadingDialog.show()
    }

    fun cancelLoadingDialog() {
        if (loadingDialog.isShowing) loadingDialog.dismiss()
    }

    /**
     * 获取权限
     */
    lateinit var permissionListener: PermissionListener

    fun requestPermission(ss: Array<String>, permissionListener: PermissionListener) {
        this.permissionListener = permissionListener
        val permissionList = ArrayList<String>()
        for (i in ss.indices) {
            if (ContextCompat.checkSelfPermission(this, ss[i]) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(ss[i])
            }
        }

        if (permissionList.size > 0) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(arrayOfNulls(permissionList.size)), permissionListener.PERMISSION)
        } else {
            permissionListener.onGranted()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionListener.PERMISSION) {
            var stringBuilder = StringBuilder()
            var permissionB = true
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    permissionB = false
                    stringBuilder.append(permissions[i]).append("，")
                }
            }

            if (permissionB) {
                permissionListener.onGranted()
            } else {
                permissionListener.onFature(stringBuilder.toString())
            }
        }
    }
}