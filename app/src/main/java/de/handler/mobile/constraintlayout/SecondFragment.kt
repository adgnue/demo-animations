package de.handler.mobile.constraintlayout

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import de.handler.mobile.example_constraintlayout.R

class SecondFragment : Fragment() {
	private lateinit var bubble: BubbleView
    private lateinit var constraintLayout: ConstraintLayout

    private val originalConstraints = ConstraintSet()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // listen to the end of the SharedElementTransition - unfortunately it can only be listened to it here
        val transitionSet = sharedElementEnterTransition as TransitionSet
        transitionSet.addListener(object : TransitionEndListener() {
            override fun onTransitionEnd(transition: Transition) {
            }
        })
    }

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater!!.inflate(R.layout.fragment_second, container, false)
	}

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (view == null) {
			return
		}

		bubble = view.findViewById(R.id.fragment_second_bubble)
        val switchButton = view.findViewById<Button>(R.id.fragment_second_switch_fragment_button)
        switchButton.setOnClickListener({ (context as MainActivity).switchFragments(FirstFragment(), arrayOf(bubble, switchButton)) })
        constraintLayout = view.findViewById(R.id.fragment_second_constraint_layout)

        // preserve original unanimated constraint state
        originalConstraints.clone(constraintLayout)
    }
	}
}