package de.handler.mobile.constraintlayout

import android.content.Context
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

class SecondFragment : BackAwareFragment() {
    private lateinit var circle: CircleView
    private lateinit var constraintLayout: ConstraintLayout

    private val originalConstraints = ConstraintSet()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // listen to the end of the SharedElementTransition - unfortunately it can only be listened to it here
        val transitionSet = sharedElementEnterTransition as TransitionSet
        transitionSet.addListener(object : TransitionEndListener() {
            override fun onTransitionEnd(transition: Transition) {
                // augment right button margin animation
                val animatedConstraints = ConstraintSet()
                animatedConstraints.clone(constraintLayout)
                TransitionManager.beginDelayedTransition(constraintLayout)
                animatedConstraints.setMargin(R.id.fragment_second_switch_fragment_button, ConstraintSet.END, 128)
                animatedConstraints.applyTo(constraintLayout)
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

        circle = view.findViewById(R.id.fragment_second_circle)
        val switchButton = view.findViewById<Button>(R.id.fragment_second_switch_fragment_button)
        switchButton.setOnClickListener({ (context as MainActivity).switchFragments(FirstFragment(), arrayOf(circle, switchButton)) })
        constraintLayout = view.findViewById(R.id.fragment_second_constraint_layout)

        // preserve original unanimated constraint state
        originalConstraints.clone(constraintLayout)
    }

    override fun onBackPressed(onBackPressedListener: OnBackPressedListener?) {
        // Create custom standard transition to be able to listen to its end
        val transition = AutoTransition()
        transition.addListener(object : TransitionEndListener() {
            override fun onTransitionEnd(transition: Transition) {
                onBackPressedListener?.onBackPressed()
            }
        })

        TransitionManager.beginDelayedTransition(constraintLayout, transition)
        originalConstraints.applyTo(constraintLayout)
    }
}