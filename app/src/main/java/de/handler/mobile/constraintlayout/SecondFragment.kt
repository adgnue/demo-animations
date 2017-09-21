package de.handler.mobile.constraintlayout

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import de.handler.mobile.example_constraintlayout.R

class SecondFragment : Fragment() {
	private lateinit var bubble: BubbleView

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater!!.inflate(R.layout.fragment_second, container, false)
	}

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (view == null) {
			return
		}
		bubble = view.findViewById(R.id.fragment_second_bubble)
		val button = view.findViewById<Button>(R.id.fragment_second_switch_fragment_button)
		button.setOnClickListener({ (context as MainActivity).switchFragments(FirstFragment(), arrayOf(bubble, button)) })
	}
}