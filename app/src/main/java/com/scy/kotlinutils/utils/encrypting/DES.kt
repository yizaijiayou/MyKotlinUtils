package com.scy.kotlinutils.utils.encrypting

import java.net.URLEncoder
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec

/**
 * 项 目 名: MyKotlinUtils
 * 创 建 人: 艺仔加油
 * 创建时间: 2018/5/8 17:23
 * 本类描述:
 */

val DES_KEY = "HJZF1234"
val CHARSET = "UTF-8"

/**
 * DES加密
 */
fun DES(msg: String) {
    val s = URLEncoder.encode(msg, CHARSET)
    toHexString(encrypt(MD5(DES_KEY), s))
}

private fun encrypt(key: String, msg: String): ByteArray {
    val bys = key.toByteArray(charset(CHARSET))
    val cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")
    val desKeySpec = DESKeySpec(bys)
    val keyFactory = SecretKeyFactory.getInstance("DES")
    val secretKey = keyFactory.generateSecret(desKeySpec)
    val iv = IvParameterSpec(bys)
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)
    return cipher.doFinal(msg.toByteArray(charset(CHARSET)))
}

private fun toHexString(bys: ByteArray): String {
    val hexString = StringBuffer()
    for (i in bys.indices) {
        var plainText = Integer.toHexString(0xFF and bys[i].toInt())
        if (plainText.length < 2) plainText = "0$plainText"
        hexString.append(plainText)
    }
    return hexString.toString()
}

/**
 * DES解密
 */

fun DESun(msg: String) {
    val s = URLEncoder.encode(msg, CHARSET)
    decrypt(MD5(DES_KEY), s)
}

private fun decrypt(key: String, msg: String): String {
    val bytesrc = convertHexString(msg)
    val bys = key.toByteArray(charset(CHARSET))
    val cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")
    val desKeySpec = DESKeySpec(bys)
    val keyFactory = SecretKeyFactory.getInstance("DES")
    val secretKey = keyFactory.generateSecret(desKeySpec)
    val iv = IvParameterSpec(bys)
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)
    val retByte = cipher.doFinal(bytesrc)
    return String(retByte)
}

private fun convertHexString(msg: String): ByteArray {
    val digest = ByteArray(msg.length / 2)
    for (i in digest.indices) {
        val byteString = msg.substring(2 * i, 2 * i + 2)
        val byteValue = Integer.parseInt(byteString,16)
        digest[i] = byteValue.toByte()
    }
    return digest
}
