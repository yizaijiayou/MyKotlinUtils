package com.scy.kotlinutils.utils

import android.content.Context
import android.os.Environment
import com.scy.kotlinutils.utils.base.BaseAppliation
import java.io.File
import java.text.DecimalFormat

/**
 * 项 目 名: MyKotlinUtils
 * 创 建 人: 艺仔加油
 * 创建时间: 2018/5/7 18:42
 * 本类描述:
 */
class FileDir {
    companion object {
        /* /storage/emulated/0 */
        val STORAGE = Environment.getExternalStorageDirectory().toString()

        /**
         * 内部存储路径（相当于系统盘）
         */
        /* /data/user/0/com.scy.myappupdate/files */
        val appFiles: String get() = BaseAppliation.appContext!!.filesDir.absolutePath

        /* /data/user/0/com.scy.myappupdate/cache */
        val appCache: String get() = BaseAppliation.appContext!!.cacheDir.absolutePath

        /**
         * 外部存储
         */
        /* /storage/emulated/0/Android/data/com.scy.myappupdate/files */
        val storeageFiles: String get() = BaseAppliation.appContext!!.getExternalFilesDir("").absolutePath

        /* /storage/emulated/0/Android/data/com.scy.myappupdate/cache */
        val storageCache: String get() = BaseAppliation.appContext!!.externalCacheDir.absolutePath

        /**
         * 图库位置
         */

        /* /storage/emulated/0/DCIM */
        /* /storage/emulated/0/DCIM/protectorPhoto    加了path之后*/
        fun DCIM(path: String): String {
            return "/storage/emulated/0/DCIM/$path"
        }

        /**
         * 获取缓存
         */
        fun getDisk(context: Context):String{
            return getDiskSize(getFolderSize(File(STORAGE,context.packageName))
            + getFolderSize(BaseAppliation.appContext!!.cacheDir)
            + getFolderSize(BaseAppliation.appContext!!.filesDir))
        }

        //单位转换
        private fun getDiskSize(size: Long): String {
            val df = DecimalFormat("######0.00")//保留两位小数
            val b = (size / 1024).toDouble()
            return df.format(b / 1024) + "MB"
        }

        private fun getFolderSize(file: File): Long {
            var size:Long = 0
            try{
                val fileLists = file.listFiles()
                for (fileList in fileLists){
                    if (fileList.isDirectory){
                        size += getFolderSize(fileList)
                    }else{
                        size += fileList.length()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
            return size
        }

        /**
         * 删除文件
         */
        fun clearDisk(context: Context){
            deleteFolder(File(STORAGE + context.packageName))
            deleteFolder(BaseAppliation.appContext!!.filesDir)
            deleteFolder(BaseAppliation.appContext!!.cacheDir)
        }

        private fun deleteFolder(file: File) {
            try {
                val fileLists = file.listFiles()
                for (fileList in fileLists){
                    if (fileList.isDirectory) {
                        deleteFolder(fileList)
                    }else{
                        fileList.delete()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}