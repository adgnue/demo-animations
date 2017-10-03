package de.handler.mobile.constraintlayout.ui

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
import de.handler.mobile.constraintlayout.animation.TransitionEndListener
import de.handler.mobile.example_constraintlayout.R

class SecondFragment : BackAwareFragment() {
    private lateinit var circle: CircleView
    private lateinit var constraintLayout: ConstraintLayout

    private val originalConstraints = ConstraintSet()
    // Create a standard transition
    private val transition = AutoTransition()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // listen to the end of the SharedElementTransition - unfortunately it can only be listened to it here
        if (sharedElementEnterTransition == null) {
            return
        }
        val transitionSet = sharedElementEnterTransition as TransitionSet?
        transitionSet?.addListener(object : TransitionEndListener() {
            override fun onTransitionEnd(transition: Transition) {
                // TODO Add your animations here
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
        constraintLayout = view.findViewById(R.id.fragment_second_constraint_layout)

        // Define switch button behaviour
        val switchButton = view.findViewById<Button>(R.id.fragment_second_switch_fragment_button)
        switchButton.setOnClickListener({ (context as MainActivity).switchFragments(FirstFragment(), arrayOf(circle, switchButton)) })

        // preserve original constraint state prior to any animation
        originalConstraints.clone(constraintLayout)
    }

    override fun onBackPressed(onBackPressedListener: OnBackPressedListener?) {
        transition.addListener(object : TransitionEndListener() {
            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                onBackPressedListener?.onBackPressed()
            }
        })

        // Animate restoring of original state
        TransitionManager.beginDelayedTransition(constraintLayout, transition)
        originalConstraints.applyTo(constraintLayout)
    }
}