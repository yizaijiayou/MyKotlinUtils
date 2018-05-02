package com.scy.kotlinutils.utils.base

import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.scy.kotlinutils.utils.ToastUtils
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //竖屏显示
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContentView(getContentLayoutId)
        init(savedInstanceState)
        doWork()
    }

    fun toast(msg: String) {
        ToastUtils.show(msg)
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
            if (permissionList != null) permissionListener.onGranted()
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
                if (permissionListener != null) permissionListener.onGranted()
            } else {
                if (permissionListener != null) permissionListener.onFature(stringBuilder.toString())
            }
        }
    }
}