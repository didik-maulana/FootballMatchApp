package com.didik.footballmatchschedule.view.event

import com.didik.footballmatchschedule.model.Event

interface EventSearchView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
    fun showEmptyEvent()
}