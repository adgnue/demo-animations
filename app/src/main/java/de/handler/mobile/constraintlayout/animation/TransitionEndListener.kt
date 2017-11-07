package de.handler.mobile.constraintlayout.animation

import android.transition.Transition

abstract class TransitionEndListener : TransitionListener() {
    override fun onTransitionStart(transition: Transition) {}
}