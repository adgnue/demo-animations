package de.handler.mobile.constraintlayout


import android.os.Bundle
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import de.handler.mobile.example_constraintlayout.R


class FirstFragment : BackAwareFragment() {
	private val transitionListener: Transition.TransitionListener = object : Transition.TransitionListener {
		override fun onTransitionEnd(transition: Transition) {
			transition.removeListener(this)
			circle.shrink(object : Transition.TransitionListener {
				override fun onTransitionEnd(transition1: Transition?) {
					transitionRunning = false
					transition.removeListener(this)
				}

				override fun onTransitionResume(transition1: Transition?) {}
				override fun onTransitionPause(transition1: Transition?) {}
				override fun onTransitionCancel(transition1: Transition?) {}
				override fun onTransitionStart(transition1: Transition?) {}
			})
		}

		override fun onTransitionResume(transition: Transition) {}
		override fun onTransitionPause(transition: Transition) {}
		override fun onTransitionCancel(transition: Transition) {}
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
		val replayButton = view.findViewById<Button>(R.id.fragment_first_replay_button)
		replayButton.setOnClickListener({ animateBubble(true) })
		animateBubble()

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
			// Post delayed if starting the app to be able to actually see the animation
			circle.postDelayed({
				startAnimation()
			}, 2000)
		}
	}

	private fun startAnimation() {
		circle.grow(transitionListener)
	}
}
