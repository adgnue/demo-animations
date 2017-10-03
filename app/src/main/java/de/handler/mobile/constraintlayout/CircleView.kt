package de.handler.mobile.constraintlayout

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View

import de.handler.mobile.example_constraintlayout.R

class CircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
	private lateinit var constraintLayout: ConstraintLayout
	// Original constraintSet to reset bubble to original state
	private val originalStateConstraintSet = ConstraintSet()
	private val transition = AutoTransition()

	init {
		init()
	}

	companion object {
		val DURATION: Long = 1000
	}

	// Make bubble round
	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
		val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
		val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
		val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

		val size: Int
		when {
			widthMode == View.MeasureSpec.EXACTLY && widthSize > 0 -> size = widthSize
			heightMode == View.MeasureSpec.EXACTLY && heightSize > 0 -> size = heightSize
			else -> size = if (widthSize < heightSize) widthSize else heightSize
		}

		val finalMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY)
		super.onMeasure(finalMeasureSpec, finalMeasureSpec)
	}

	fun grow(listener: Transition.TransitionListener?) {
		val stretchStateConstraintSet = ConstraintSet()
		stretchStateConstraintSet.clone(constraintLayout)
		if (listener != null) {
			transition.addListener(listener)
		}
		TransitionManager.beginDelayedTransition(this, transition)
		// stretch the bubble with its own guidelines
		stretchStateConstraintSet.setGuidelinePercent(R.id.view_circle_guideline_left_background, -0.5f)
		stretchStateConstraintSet.setGuidelinePercent(R.id.view_circle_guideline_top_background, -0.99f)
		stretchStateConstraintSet.setGuidelinePercent(R.id.view_circle_guideline_right_background, 1.3f)
		stretchStateConstraintSet.applyTo(constraintLayout)
	}

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
		transition.duration = DURATION
		// Clone original state
		originalStateConstraintSet.clone(constraintLayout)
	}
}
