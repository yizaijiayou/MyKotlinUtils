package com.scy.kotlinutils.utils.base

import android.app.Fragment
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scy.dialogloading.LoadingDialog
import com.scy.kotlinutils.R
import com.scy.kotlinutils.utils.ToastUtils
import com.scy.kotlinutils.utils.sql.SharedPreferencesUtils
import com.scy.kotlinutils.utils.sql.sqlite.ProjectSQLite
import java.lang.StringBuilder
import java.util.ArrayList

/**
 * 项 目 名: MyKotlinUtils
 * 创 建 人: 艺仔加油
 * 创建时间: 2018/5/7 18:19
 * 本类描述:
 */
abstract class BaseFragment : Fragment() {
    //获取layout文件
    abstract val getContentLayoutId: Int

    //初始化参数
    abstract fun init(savedInstanceState: Bundle?)

    //操作方法
    abstract fun doWork()

    lateinit var loadingDialog: LoadingDialog
    lateinit var sharedPreferences: SharedPreferencesUtils
    lateinit var sqLiteDatabase: SQLiteDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View {
        loadingDialog = LoadingDialog(activity, ContextCompat.getColor(activity, R.color.colorAccent))
        sharedPreferences = SharedPreferencesUtils(activity)
        val projectSQLite = ProjectSQLite(activity, ProjectSQLite.SQLNAME, ProjectSQLite.SQLVERSION)
        sqLiteDatabase = projectSQLite.readableDatabase

        val view = LayoutInflater.from(activity).inflate(getContentLayoutId, container, false)
        init(savedInstanceState)
        doWork()

        return view
    }

    fun toast(msg: String) {
        (activity as BaseActivity).toast(msg)
//        ToastUtils.show(msg)
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
    fun requestPermission(ss: Array<String>, permissionListener: PermissionListener) {
        (activity as BaseActivity).requestPermission(ss,permissionListener)
    }
}