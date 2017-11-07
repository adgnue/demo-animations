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
                val animatedConstraints = ConstraintSet()
                animatedConstraints.clone(constraintLayout)

                TransitionManager.beginDelayedTransition(constraintLayout)
                // augment right button margin animation
                animatedConstraints.setMargin(R.id.fragment_second_switch_fragment_button, ConstraintSet.END, 128)

                // center button vertically animation
//                animatedConstraints.centerVertically(R.id.fragment_second_switch_fragment_button, R.id.fragment_second_circle)

                // grow width & height animation
//                fragment_second_switch_fragment_button?.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
//                animatedConstraints.constrainWidth(R.id.fragment_second_switch_fragment_button, 1500)
//                animatedConstraints.constrainWidth(R.id.fragment_second_circle, 2500)
//                animatedConstraints.constrainHeight(R.id.fragment_second_circle, 2500)

                // center in circle animation
//                animatedConstraints.connect(
//                        R.id.fragment_second_switch_fragment_button, ConstraintSet.START,
//                        R.id.fragment_second_constraint_layout, ConstraintSet.START, 16)
//                animatedConstraints.centerVertically(R.id.fragment_second_switch_fragment_button, R.id.fragment_second_circle)

                // grow button to full screen size
//                fragment_second_switch_fragment_button?.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
//                animatedConstraints.clear(R.id.fragment_second_switch_fragment_button)
//                animatedConstraints.connect(
//                        R.id.fragment_second_switch_fragment_button, ConstraintSet.START,
//                        R.id.fragment_second_constraint_layout, ConstraintSet.START, 0)
//                animatedConstraints.connect(
//                        R.id.fragment_second_switch_fragment_button, ConstraintSet.END,
//                        R.id.fragment_second_constraint_layout, ConstraintSet.END, 0)
//                animatedConstraints.connect(
//                        R.id.fragment_second_switch_fragment_button, ConstraintSet.TOP,
//                        R.id.fragment_second_constraint_layout, ConstraintSet.TOP, 0)
//                animatedConstraints.connect(
//                        R.id.fragment_second_switch_fragment_button, ConstraintSet.BOTTOM,
//                        R.id.fragment_second_constraint_layout, ConstraintSet.BOTTOM, 0)

                // create a chain of the two elements and align them next to each other
                // reset constraints and set height / width to necessary values
//                animatedConstraints.clear(R.id.fragment_second_switch_fragment_button)
//                animatedConstraints.connect(
//                        R.id.fragment_second_switch_fragment_button, ConstraintSet.LEFT,
//                        R.id.fragment_second_constraint_layout, ConstraintSet.LEFT, 0)
//                animatedConstraints.connect(
//                        R.id.fragment_second_switch_fragment_button, ConstraintSet.RIGHT,
//                        R.id.fragment_second_circle, ConstraintSet.LEFT, 0)
//                animatedConstraints.constrainWidth(R.id.fragment_second_switch_fragment_button, 0)
//                animatedConstraints.constrainHeight(R.id.fragment_second_switch_fragment_button, ConstraintSet.WRAP_CONTENT)
//
//                animatedConstraints.clear(R.id.fragment_second_circle)
//                animatedConstraints.connect(
//                        R.id.fragment_second_circle, ConstraintSet.LEFT,
//                        R.id.fragment_second_switch_fragment_button, ConstraintSet.RIGHT, 0)
//                animatedConstraints.connect(
//                        R.id.fragment_second_circle, ConstraintSet.RIGHT,
//                        R.id.fragment_second_constraint_layout, ConstraintSet.RIGHT, 0)
//                animatedConstraints.constrainWidth(R.id.fragment_second_circle, 0)
//                animatedConstraints.constrainHeight(R.id.fragment_second_circle, ConstraintSet.WRAP_CONTENT)
//
//                // create chain
//                animatedConstraints.createHorizontalChain(
//                        ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
//                        ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
//                        intArrayOf(R.id.fragment_second_switch_fragment_button, R.id.fragment_first_circle),
//                        null,
//                        ConstraintWidget.CHAIN_SPREAD)

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