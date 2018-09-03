package com.scy.kotlinutils.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.scy.kotlinutils.utils.base.BaseAppliation

/**
 * 项 目 名: MyKotlinUtils
 * 创 建 人: 艺仔加油
 * 创建时间: 2018/5/8 15:10
 * 本类描述:
 */
object GlideUtils{
    /**
     * 加载图片
     */
    fun load(context: Context,url:String,imageView: ImageView){
        Glide.with(context).load(url).into(imageView)
    }

    fun loadCircle(context: Context,url:String,imageView: ImageView){
        val requestOption = RequestOptions()
        requestOption.circleCrop()
        Glide.with(context).load(url).apply(requestOption).into(imageView)
    }

    /**
     * 清楚Glide所的磁盘缓存
     */
    fun clearGlideDisk(context: Context){
        Thread(Runnable { Glide.get(context).clearDiskCache() }).start()
    }
}