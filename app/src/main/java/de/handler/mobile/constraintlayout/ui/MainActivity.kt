package de.handler.mobile.constraintlayout.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import de.handler.mobile.example_constraintlayout.R

/**
 * Find out more about the different Android animation apis via
 * {@see <a href="https://github.com/codepath/android_guides/wiki/Animations">Codepath on GitHub}
 */
class MainActivity : AppCompatActivity() {
    private lateinit var transition: Transition

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // assign transition from xml
        transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.shared_element_transition)

        // start first fragment
        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container, FirstFragment())
                .commit()
    }

    // handle back press events only when the fragment has not yet done it
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

        // Set the shared element transition to the fragment
        // as enter transition
		fragment.sharedElementEnterTransition = transition
        // and as return transition
		fragment.sharedElementReturnTransition = transition

        // start fragment transaction
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main_fragment_container, fragment)
        transaction.addToBackStack(fragment.javaClass.name)

        // at the shared element views to the fragment transaction
        for (animationView in animationViews) {
            transaction.addSharedElement(animationView, animationView.transitionName)
        }

        // go for it - run the fragment transaction
        transaction.commit()
    }
}