package com.scy.kotlinutils.utils.retrofit

import android.os.Handler
import android.util.Log
import com.scy.kotlinutils.R
import com.scy.kotlinutils.utils.FileDir
import com.scy.kotlinutils.utils.base.BaseAppliation
import com.scy.kotlinutils.utils.retrofit.cookie.PersistentCookieStore
import com.zxy.tiny.Tiny
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.observers.BlockingBaseObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit

class Retrofit2Utils private constructor() {
    private val TIMEOUT = 30.toLong()
    private val retrofit: Retrofit

    companion object {
        private var isCookie = false
        private var retrofit2Utils: Retrofit2Utils? = null

        val instance: Retrofit2Utils
            get() {
                if (retrofit2Utils == null) retrofit2Utils = Retrofit2Utils()
                return retrofit2Utils!!
            }

        fun create(): Reception {
            return instance.retrofit.create(Reception::class.java)
        }

        fun create(isCookie: Boolean): Reception {
            if (isCookie) this.isCookie = true
            return instance.retrofit.create(Reception::class.java)
        }
    }

    init {
        val okHttpClent = OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//错误重连
                .addInterceptor(MyHttpLoggingInterceptor())
                .cookieJar(object : CookieJar {
                    private val cookieStore = PersistentCookieStore(BaseAppliation.appContext)

                    override fun saveFromResponse(url: HttpUrl?, cookies: MutableList<Cookie>?) {
                        if (isCookie) {
                            isCookie = false
                            if (cookies != null && cookies.size > 0) {
                                for (item in cookies) {
                                    cookieStore.add(url, item)
                                }
                            }
                        }
                    }

                    override fun loadForRequest(url: HttpUrl?): MutableList<Cookie> {
                        return cookieStore.cookies
                    }
                })
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(Reception.ip)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClent)
                .build()
    }

    fun upload(files: Array<File>, map: Map<String, String>, getJsonObject: GetJsonObject<String>) {
        val option = Tiny.FileCompressOptions()
        Tiny.getInstance().source(files).batchAsFile().withOptions(option).batchCompress { isSuccess, outfile, t ->
            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)

            //加参数
            val set = map.keys
            for (s in set) {
                builder.addFormDataPart(s, map[s])
            }

            //加图片
            for (i in files.indices) {
                builder.addFormDataPart("image", "photo$i.jpg", RequestBody.create(MediaType.parse("application/octet-stream"), File(outfile[i]))).build()
            }


            val part = builder.build().parts()
            retrofit.create(Reception::class.java)
                    .upload(part)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BlockingBaseObserver<String>() {
                        override fun onNext(s: String) {
                            getJsonObject.getJsonObject(s)
                        }

                        override fun onError(e: Throwable) {
                            Log.e("Retrofit2Utils", "---->" + e.message)
                        }
                    })
        }
    }

    fun upload(file: File, map: Map<String, String>, getJsonObject: GetJsonObject<String>) {
        val files = arrayOf(file)
        upload(files, map, getJsonObject)
    }

    interface GetJsonObject<T> {
        fun getJsonObject(t: T)
    }

    /**
     * 下载文件
     *
     * @param downUrl            下载路径
     * @param handler            主线程
     * @param onDownloadListener 下载回调
     * API
     * @Streaming //预防大文件，所以这里用Streaming
     * @GET Observable<ResponseBody> down(@Url String fileUrl);
     */
    fun down(downUrl: String, handler: Handler, onDownloadListener: OnDownloadListener) {
        val file = File(FileDir.storeageFiles, BaseAppliation.appContext?.getString(R.string.app_name) + ".apk")

        val retrofit = Retrofit.Builder()
                .baseUrl(Reception.ip)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        retrofit.create(Reception::class.java)
                .down(downUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(object : BlockingBaseObserver<ResponseBody>() {
                    override fun onNext(responseBody: ResponseBody) {
                        var inputStream: InputStream? = null
                        var fileOutputStream: FileOutputStream? = null
                        val bys = ByteArray(1024)

                        try {

                            inputStream = responseBody.byteStream()
                            val total = responseBody.contentLength()
                            fileOutputStream = FileOutputStream(file)
                            var sum: Long = 0

                            while (inputStream.read(bys) != -1) {
                                fileOutputStream.write(bys, 0, inputStream.read(bys))
                                sum += inputStream.read(bys).toLong()
                                val progress = (sum * 1.0f / total * 100).toInt()
                                handler.post { onDownloadListener.onDownloading(progress) }
                            }

                            fileOutputStream.flush()
                            handler.post { onDownloadListener.onDownloadSuccess(file) }
                        } catch (e: Exception) {
                            handler.post { onDownloadListener.onDownloadFailed() }
                            e.printStackTrace()
                        } finally {
                            try {
                                inputStream?.close()
                                fileOutputStream?.close()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        handler.post { onDownloadListener.onDownloadFailed() }
                    }
                })
    }

    interface OnDownloadListener {
        fun onDownloadSuccess(file: File)
        fun onDownloading(progress: Int)
        fun onDownloadFailed()
    }
}