package de.handler.mobile.constraintlayout.ui


import android.os.Bundle
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import de.handler.mobile.constraintlayout.animation.TransitionEndListener
import de.handler.mobile.constraintlayout.animation.TransitionListener
import de.handler.mobile.example_constraintlayout.R


class FirstFragment : BackAwareFragment() {
	// Define what's to be done during animation
	private val transitionListener: Transition.TransitionListener = object : TransitionListener() {
		override fun onTransitionEnd(transition: Transition) {
			transition.removeListener(this)
			circle.shrink(object : TransitionEndListener() {
				override fun onTransitionEnd(transition1: Transition?) {
					transition.removeListener(this)
					transitionRunning = false
				}
			})
		}
		override fun onTransitionStart(transition: Transition) {
			transitionRunning = true
		}
	}

	private var transitionRunning: Boolean = false
	private lateinit var circle: CircleView

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater!!.inflate(R.layout.fragment_first, container, false)
	}

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (view == null) {
			return
		}
		circle = view.findViewById(R.id.fragment_first_circle)

		// Grow the bubble with a delay
		animateBubble()

		// Define replay button behaviour
		val replayButton = view.findViewById<Button>(R.id.fragment_first_replay_button)
		replayButton.setOnClickListener({ animateBubble(true) })

		// Define switch button behaviour
		val switchButton = view.findViewById<Button>(R.id.fragment_first_switch_fragment_button)
		switchButton.setOnClickListener({ (context as MainActivity).switchFragments(SecondFragment(), arrayOf(circle, switchButton)) })
	}


	private fun animateBubble(reload: Boolean = false) {
		if (transitionRunning) {
			return
		}
		if (reload) {
			startAnimation()
		} else {
			// Post delayed when starting the app to be able to actually see the animation
			circle.postDelayed({
				startAnimation()
			}, 2000)
		}
	}

	private fun startAnimation() {
		circle.grow(transitionListener)
	}
}
