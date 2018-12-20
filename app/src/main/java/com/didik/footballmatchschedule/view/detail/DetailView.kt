package com.didik.footballmatchschedule.view.detail

import com.didik.footballmatchschedule.model.Team

interface DetailView {

    fun showLoading()
    fun hideLoading()
    fun showEventDetails(detailHome: List<Team>, detailAway: List<Team>)
}
