package com.scy.kotlinutils.utils.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.Charset

class MyHttpLoggingInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requesst = chain.request()
        val response = chain.proceed(requesst)
        val responseBody = response.body()
        val source = responseBody?.source()
        source?.request(Long.MAX_VALUE)
        val buffer = source?.buffer()
        var charset = Charset.forName("UTF-8")
        val contentType = responseBody?.contentType()
        if (contentType != null) charset = contentType.charset(Charset.forName("UTF-8"))
        Log.w("httpLog",requesst.method()+"  "+requesst.url() + "\n" + (buffer?.clone()?.readString(charset)))
        return response
    }
}