package com.scy.kotlinutils.utils.encrypting

import java.lang.RuntimeException
import java.security.MessageDigest
import kotlin.experimental.and

/**
 * 项 目 名: MyKotlinUtils
 * 创 建 人: 艺仔加油
 * 创建时间: 2018/5/8 15:43
 * 本类描述:
 */
fun MD5(msg: String): String {
    val bys: ByteArray
    try {
        bys = MessageDigest.getInstance("MD5").digest(msg.toByteArray(charset("UTF-8")))
    } catch (e: Exception) {
        throw RuntimeException("MD5 fail to get", e)
    }

    val sb = StringBuffer(bys.size * 2)
    for (b in bys) {
        if (b and 0xFF.toByte() < 0x10) sb.append("0")
        sb.append(Integer.toHexString((b and 0xFF.toByte()).toInt()))
    }

    return sb.toString().toUpperCase()
}