package com.didik.footballmatchschedule.view.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.didik.footballmatchschedule.R
import kotlinx.android.synthetic.main.fragment_overview.*
import org.jetbrains.anko.bundleOf

class OverviewFragment : Fragment() {

    companion object {
        fun newInstance(data: String): OverviewFragment {
            val fragment = OverviewFragment()
            fragment.arguments = bundleOf("overview" to data)

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        team_desc.text = arguments?.getString("overview")
    }
}