package de.handler.mobile.constraintlayout.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View

import de.handler.mobile.example_constraintlayout.R

/**
 * A simple circle which extends {@link ConstraintLayout}.
 */
class CircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
	private lateinit var constraintLayout: ConstraintLayout
	// Original constraintSet to reset circle to original state
	private val originalStateConstraintSet = ConstraintSet()
	// Create a standard transition
	private val transition = AutoTransition()

	init {
		init()
	}

	// Make circle round
	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
		val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
		val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
		val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

		val size: Int
		size = when {
			widthMode == View.MeasureSpec.EXACTLY && widthSize > 0 -> widthSize
			heightMode == View.MeasureSpec.EXACTLY && heightSize > 0 -> heightSize
			else -> if (widthSize < heightSize) widthSize else heightSize
		}

		val finalMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY)
		super.onMeasure(finalMeasureSpec, finalMeasureSpec)
	}

	/**
	 * Grows the circle and adds a {@link Transition.TransitionListener} to the transition
	 * to be able to react to end of transition.
	 *
	 * Calling TransitionManager.beginDelayedTransition(this, transition) is the same as
	 * TransitionManager.beginDelayedTransition(this) but without the possibility of adding
	 * an {@link Transition.TransitionListener}
	 *
	 * The grow effect is achieved by moving the ConstraintLayout's guidelines out of
	 * the visible view range.
	 *
	 * @param listener callback for knowing the exact moment of the animation end
	 */
	fun grow(listener: Transition.TransitionListener?) {
		val stretchStateConstraintSet = ConstraintSet()
		stretchStateConstraintSet.clone(constraintLayout)
		if (listener != null) {
			transition.addListener(listener)
		}
		TransitionManager.beginDelayedTransition(this, transition)
		// stretch the circle with its own guidelines
		stretchStateConstraintSet.setGuidelinePercent(R.id.view_circle_guideline_left_background, -0.5f)
		stretchStateConstraintSet.setGuidelinePercent(R.id.view_circle_guideline_top_background, -0.99f)
		stretchStateConstraintSet.setGuidelinePercent(R.id.view_circle_guideline_right_background, 1.3f)
		stretchStateConstraintSet.applyTo(constraintLayout)
	}

    /**
     * Shrinks the circle to its original size and adds a {@link Transition.TransitionListener}
     * to the transition to be able to react to end of transition.
     *
     * Calling TransitionManager.beginDelayedTransition(this, transition) is the same as
     * TransitionManager.beginDelayedTransition(this) but without the possibility of adding
     * an {@link Transition.TransitionListener}
     *
     * The shrink effect is achieved by resetting the original ConstraintSet to the
     * {@link ConstraintLayout}. This is cloned in the init() method as soon as the view
     * gets instantiated.
     */
	fun shrink(listener: Transition.TransitionListener?) {
		if (listener != null) {
			transition.addListener(listener)
		}
		// Only available from api 19
		TransitionManager.beginDelayedTransition(this, transition)
		originalStateConstraintSet.applyTo(constraintLayout)
	}

	private fun init() {
		val view = View.inflate(context, R.layout.view_circle, this)
		constraintLayout = view.findViewById(R.id.view_circle_container)
		// Define animation duration
		transition.duration = 1000
		// Clone original state
		originalStateConstraintSet.clone(constraintLayout)
	}
}
