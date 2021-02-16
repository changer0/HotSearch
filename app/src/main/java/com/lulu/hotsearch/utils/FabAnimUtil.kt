package com.lulu.hotsearch.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import com.lulu.baseutil.bezelless.DensityUtil

object FabAnimUtil {
    fun startDisplayAnim(fabRoot: View, isIdle: Boolean): ObjectAnimator {
        var objectAnimator = if (isIdle) {
            fabRoot.visibility = View.VISIBLE
            val ofFloat = ObjectAnimator.ofFloat(
                fabRoot,
                "alpha",
                0F,
                1F
            )
            ofFloat
        } else {
            val ofFloat = ObjectAnimator.ofFloat(
                fabRoot,
                "translationY",
                0F,
                DensityUtil.dip2px(100F).toFloat()
            )
            ofFloat.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    fabRoot.visibility = View.GONE
                    fabRoot.translationY = 0F
                }
            })
            ofFloat
        }
        objectAnimator.duration = 300
        objectAnimator.start()
        return objectAnimator
    }
}