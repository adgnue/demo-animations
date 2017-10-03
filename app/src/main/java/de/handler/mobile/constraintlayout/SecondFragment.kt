package de.handler.mobile.constraintlayout

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.content.ContextCompat
import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import de.handler.mobile.example_constraintlayout.R
import kotlinx.android.synthetic.main.fragment_second.*

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
                val animatedConstraints = ConstraintSet()
                animatedConstraints.clone(constraintLayout)

                TransitionManager.beginDelayedTransition(constraintLayout)
                // augment right button margin animation
//                animatedConstraints.setMargin(R.id.fragment_second_switch_fragment_button, ConstraintSet.END, 128)

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
                fragment_second_switch_fragment_button?.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                animatedConstraints.clear(R.id.fragment_second_switch_fragment_button)
                animatedConstraints.connect(
                        R.id.fragment_second_switch_fragment_button, ConstraintSet.START,
                        R.id.fragment_second_constraint_layout, ConstraintSet.START, 0)
                animatedConstraints.connect(
                        R.id.fragment_second_switch_fragment_button, ConstraintSet.END,
                        R.id.fragment_second_constraint_layout, ConstraintSet.END, 0)
                animatedConstraints.connect(
                        R.id.fragment_second_switch_fragment_button, ConstraintSet.TOP,
                        R.id.fragment_second_constraint_layout, ConstraintSet.TOP, 0)
                animatedConstraints.connect(
                        R.id.fragment_second_switch_fragment_button, ConstraintSet.BOTTOM,
                        R.id.fragment_second_constraint_layout, ConstraintSet.BOTTOM, 0)

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