package de.handler.mobile.constraintlayout.animation

import android.transition.Transition

abstract class TransitionListener : Transition.TransitionListener {
    override fun onTransitionCancel(transition: Transition) {}
    override fun onTransitionPause(transition: Transition) {}
    override fun onTransitionResume(transition: Transition) {}
}