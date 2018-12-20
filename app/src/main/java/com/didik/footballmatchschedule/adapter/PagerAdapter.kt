package com.didik.footballmatchschedule.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class PagerAdapter(supportFragmentManager: FragmentManager, private val map: Map<String, Fragment>) : FragmentStatePagerAdapter(supportFragmentManager) {

    private val title = map.keys.toList()
    private val fragment = map.values.toList()

    override fun getItem(position: Int): Fragment = fragment[position]
    override fun getCount(): Int = map.size
    override fun getPageTitle(position: Int): CharSequence? = title[position]
}