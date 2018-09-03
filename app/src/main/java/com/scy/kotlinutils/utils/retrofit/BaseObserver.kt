package com.scy.kotlinutils.utils.retrofit

import com.scy.kotlinutils.utils.L
import com.scy.kotlinutils.utils.base.BaseBean
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open abstract class BaseObserver<T> : Observer<BaseBean<T>> {

    val SOCKET_TIMEOUT_EXCEPTION = "网络连接超时，请检查您的网络状态，稍后重试"
    val CONNECT_EXCEPTION = "网络连接异常，请检查您的网络状态"
    val UNKNOWN_HOST_EXCEPTION = "网络异常，请检查您的网络状态"

    override fun onComplete() {
        L.e("BaseObserver-----------------------------onComplete")
        onFailure("")
    }

    override fun onSubscribe(d: Disposable) {
        L.e("BaseObserver-----------------------------onSubscribe")
    }

    override fun onNext(t: BaseBean<T>) {
        L.e("BaseObserver-----------------------------onNext")
        if (t.Result) {
            onSuccess(t.Data)
        } else {
            onFailure(t.Msg)
        }
    }

    override fun onError(e: Throwable) {
        L.e("BaseObserver-----------------------------onError")
        when (e) {
            is SocketTimeoutException -> onFailure(SOCKET_TIMEOUT_EXCEPTION)
            is ConnectException -> onFailure(CONNECT_EXCEPTION)
            is UnknownHostException -> onFailure(UNKNOWN_HOST_EXCEPTION)
            else -> onFailure(e.message)
        }
    }

    abstract fun onSuccess(t: T?)
    abstract fun onFailure(e: String?)

}