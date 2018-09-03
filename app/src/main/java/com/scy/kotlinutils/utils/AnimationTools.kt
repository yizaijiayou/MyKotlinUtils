package com.scy.kotlinutils.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator

/**
 * 项 目 名: MyKotlinUtils
 * 创 建 人: 艺仔加油
 * 创建时间: 2018/5/8 15:29
 * 本类描述:
 */
object AnimationTools {
    /**
     * 透明度
     * @param target
     * @param duration
     * @param values
     */
    fun alpha(target: Any, duration: Long, vararg values: Float) {
        val objectAnimator = ObjectAnimator.ofFloat(target, "alpha", *values)
        objectAnimator.duration = duration
        objectAnimator.start()
    }
    /**
     * 旋转
     * @param target
     * @param duration
     * @param values
     */
    fun rotation(target: Any, duration: Long, vararg values: Float) {
        val objectAnimator = ObjectAnimator.ofFloat(target, "rotation", *values)
        objectAnimator.duration = duration
        objectAnimator.start()
    }

    /**
     * 围绕 X 轴旋转
     * @param target
     * @param duration
     * @param values
     */
    fun rotationX(target: Any, duration: Long, vararg values: Float) {
        val objectAnimator = ObjectAnimator.ofFloat(target, "rotationX", *values)
        objectAnimator.duration = duration
        objectAnimator.start()
    }

    /**
     * 围绕 Y 轴旋转
     * @param target
     * @param duration
     * @param values
     */
    fun rotationY(target: Any, duration: Long, vararg values: Float) {
        val objectAnimator = ObjectAnimator.ofFloat(target, "rotationY", *values)
        objectAnimator.duration = duration
        objectAnimator.start()
    }

    /**
     * 正数向右平移，负数向左平移
     * @param target
     * @param duration
     * @param values
     */
    fun translationX(target: Any, duration: Long, vararg values: Float) {
        val objectAnimator = ObjectAnimator.ofFloat(target, "translationX", *values)
        objectAnimator.duration = duration
        objectAnimator.start()
    }

    /**
     * 正数向下平移，负数向上平移
     * @param target
     * @param duration
     * @param values
     */
    fun translationY(target: Any, duration: Long, vararg values: Float) {
        val objectAnimator = ObjectAnimator.ofFloat(target, "translationY", *values)
        objectAnimator.duration = duration
        objectAnimator.start()
    }

    /**
     * X 轴缩放
     * @param target
     * @param duration
     * @param values
     */
    fun scaleX(target: Any, duration: Long, vararg values: Float) {
        val objectAnimator = ObjectAnimator.ofFloat(target, "scaleX", *values)
        objectAnimator.duration = duration
        objectAnimator.start()
    }

    /**
     * Y 轴缩放
     * @param target
     * @param duration
     * @param values
     */
    fun scaleY(target: Any, duration: Long, vararg values: Float) {
        val objectAnimator = ObjectAnimator.ofFloat(target, "scaleY", *values)
        objectAnimator.duration = duration
        objectAnimator.start()
    }

    /**
     * XY 轴缩放
     * @param target
     * @param duration
     * @param values
     */
    fun scaleXY(target: Any, duration: Long, vararg values: Float) {
        val objectAnimatorX = ObjectAnimator.ofFloat(target, "scaleY", *values)
        val objectAnimatorY = ObjectAnimator.ofFloat(target, "scaleX", *values)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(objectAnimatorX, objectAnimatorY)
        animatorSet.duration = duration
        animatorSet.start()
    }
}