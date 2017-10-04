package de.handler.mobile.constraintlayout

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.transition.ChangeBounds
import android.transition.ChangeClipBounds
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.view.View
import de.handler.mobile.example_constraintlayout.R

/**
 * Find out more about the different Android animation apis via
 * {@see <a href="https://github.com/codepath/android_guides/wiki/Animations">Codepath on GitHub}
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container, FirstFragment())
                .commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.activity_main_fragment_container)
        if (fragment is BackAwareFragment) {
            fragment.onBackPressed(object : BackAwareFragment.OnBackPressedListener {
                override fun onBackPressed(handled: Boolean) {
                    if (!handled) {
                        super@MainActivity.onBackPressed()
                    }
                }
            })
        } else {
            super.onBackPressed()
        }
    }

    fun switchFragments(fragment: Fragment, animationViews: Array<View>) {
        // Don't start another transaction if the fragment is already on the backstack --> prevent same fragment instances on the stack
        // Works only for two fragments of course
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            return
        }

        val bubbleTransition = TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeTransform())
                .addTransition(ChangeClipBounds())

        fragment.sharedElementEnterTransition = bubbleTransition
        fragment.sharedElementReturnTransition = bubbleTransition

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main_fragment_container, fragment)
        transaction.addToBackStack(fragment.javaClass.name)

        for (animationView in animationViews) {
            transaction.addSharedElement(animationView, animationView.transitionName)
        }

        transaction.commit()
    }
}