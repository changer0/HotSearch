package com.tencent.util;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/**
 * 粘性流体算法. 从scroller里扣出来的. 使用这个算法处理滚动
 * 视觉效果会比AccelerateDecelerateInterpolator视觉效果要好
 * 
 * 
 */
public final class AnimateUtils {
	private static float START_TENSION = 0.4f; // Tension at start: (0.4 * total
												// T, 1.0 * Distance)
	private static float END_TENSION = 1.0f - START_TENSION;
	private static final int NB_SAMPLES = 100;
	private static final float[] SPLINE = new float[NB_SAMPLES + 1];

	private static float sViscousFluidScale;
	private static float sViscousFluidNormalize;

	static {
		float x_min = 0.0f;
		for (int i = 0; i <= NB_SAMPLES; i++) {
			final float t = (float) i / NB_SAMPLES;
			float x_max = 1.0f;
			float x, tx, coef;
			while (true) {
				x = x_min + (x_max - x_min) / 2.0f;
				coef = 3.0f * x * (1.0f - x);
				tx = coef * ((1.0f - x) * START_TENSION + x * END_TENSION) + x
						* x * x;
				if (Math.abs(tx - t) < 1E-5)
					break;
				if (tx > t)
					x_max = x;
				else
					x_min = x;
			}
			final float d = coef + x * x * x;
			SPLINE[i] = d;
		}
		SPLINE[NB_SAMPLES] = 1.0f;

		// This controls the viscous fluid effect (how much of it)
		sViscousFluidScale = 8.0f;
		// must be set to 1.0 (used in viscousFluid())
		sViscousFluidNormalize = 1.0f;
		sViscousFluidNormalize = 1.0f / viscousFluid(1.0f);
	}

	private AnimateUtils() {

	}

	/**
	 * for滚动动画, 这个算法会比较平滑
	 * 
	 * @param x
	 * @return
	 */
	public static float viscousFluid(float x) {
		x *= sViscousFluidScale;
		if (x < 1.0f) {
			x -= (1.0f - (float) Math.exp(-x));
		} else {
			float start = 0.36787944117f; // 1/e == exp(-1)
			x = 1.0f - (float) Math.exp(1.0f - x);
			x = start + x * (1.0f - start);
		}
		x *= sViscousFluidNormalize;
		return x;
	}

	/**
	 * AnimationListener的Adapter, 一般监听动画结束时使用, 避免每次内部类都实现3个方法..可以减少方法数
	 * 
	 * @author ncikywan
	 * 
	 */
	public static class AnimationAdapter implements AnimationListener {

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO 自动生成的方法存根
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO 自动生成的方法存根
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO 自动生成的方法存根
		}

	}
}
