package com.didik.footballmatchschedule.view.event

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.didik.footballmatchschedule.view.team.TabTeam

class Pager(fm: FragmentManager, private var tabCount: Int) :
        FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        return when (position) {
            0 -> TabEvent()
            1 -> TabTeam()
            else -> null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}