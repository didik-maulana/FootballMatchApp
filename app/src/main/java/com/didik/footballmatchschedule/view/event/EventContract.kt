package com.didik.footballmatchschedule.view.event

import android.content.Context
import com.didik.footballmatchschedule.model.Event
import com.didik.footballmatchschedule.model.LeagueResponse

interface EventView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
    fun showLeague(data: LeagueResponse)
    fun showEmptyEvent()
}

abstract class EventPresentation {
    abstract fun getPastEventList(leagueId: String)
    abstract fun getNextEventList(leagueId: String)
    abstract fun getFavEventList(context: Context)
    abstract fun getLeague()
}