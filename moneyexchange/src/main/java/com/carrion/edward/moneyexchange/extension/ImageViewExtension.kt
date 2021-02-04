package com.carrion.edward.moneyexchange.extension

import android.view.View
import android.view.animation.RotateAnimation

/**
 * rotate 180 degrees
 */
fun View.rotate() {
    val rotateAnimation = RotateAnimation(0f, 180f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f)
    rotateAnimation.duration = 500
    this.startAnimation(rotateAnimation)
}