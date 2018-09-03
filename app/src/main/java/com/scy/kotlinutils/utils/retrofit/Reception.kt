package com.scy.kotlinutils.utils.retrofit

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface Reception {

    companion object {
        const val ip = "http://113.106.11.187:9086"

        const val UserControl = "/ajax/ZK/UserControl.ashx"
        const val InterfaceApp = "/ajax/ZK/InterfaceApp.ashx"
    }

    /**
     * 上传文件
     */
    @Multipart
    @POST(InterfaceApp)
    fun upload(@Part multipart: List<MultipartBody.Part>): Observable<String>

    /**
     * 下载文件
     */
    @Streaming
    @GET
    fun down(@Url fileUrl: String): Observable<ResponseBody>

}