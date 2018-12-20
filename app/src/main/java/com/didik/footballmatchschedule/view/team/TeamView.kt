package com.didik.footballmatchschedule.view.team

import com.didik.footballmatchschedule.model.LeagueResponse
import com.didik.footballmatchschedule.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
    fun showLeague(data: LeagueResponse)
    fun showEmptyTeam()
    fun searchEmpty()
}
