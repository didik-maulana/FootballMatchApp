package com.didik.footballmatchschedule.view.detail

import com.didik.footballmatchschedule.model.Team

interface DetailTeamView {

    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(detailTeam: List<Team>)
}