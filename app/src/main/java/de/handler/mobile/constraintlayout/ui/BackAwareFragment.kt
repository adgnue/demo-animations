package de.handler.mobile.constraintlayout.ui

import android.support.v4.app.Fragment

open class BackAwareFragment : Fragment() {
    open fun onBackPressed(onBackPressedListener: OnBackPressedListener?) {
        onBackPressedListener?.onBackPressed()
    }

    interface OnBackPressedListener {
        fun onBackPressed(handled: Boolean = false)
    }
}