package com.didik.footballmatchschedule.presenter

import android.content.Context
import android.util.Log
import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.api.TSDBRest
import com.didik.footballmatchschedule.database.database
import com.didik.footballmatchschedule.model.LeagueResponse
import com.didik.footballmatchschedule.model.Team
import com.didik.footballmatchschedule.model.TeamResponse
import com.didik.footballmatchschedule.utils.CoroutineContextProvider
import com.didik.footballmatchschedule.view.team.TeamView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class TeamPresenter(private val view: TeamView,
                    private val tsdbRepository: TSDBRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    var menu = 1

    fun getLeague() {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(tsdbRepository
                        .doRequest(TSDBRest.leagueAll()),
                        LeagueResponse::class.java)
            }
            view.hideLoading()
            view.showLeague(data.await())
        }
    }

    fun getTeam(league: String = "") {
        menu = 1
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(tsdbRepository
                        .doRequest(TSDBRest.teamAll(league)),
                        TeamResponse::class.java)
            }
            view.hideLoading()
            view.showTeamList(data.await().teams)
            Log.e("Data", data.await().teams.toString())
        }
    }

    fun getFavorite(context: Context) {
        menu = 2
        view.showLoading()
        val data: MutableList<Team> = mutableListOf()
        context.database.use {
            val favorite = select(Team.TABLE_FAVORITE)
                    .parseList(classParser<Team>())
            data.addAll(favorite)
        }
        view.hideLoading()

        if (data.size > 0) {
            view.showTeamList(data)
        } else {
            view.showEmptyTeam()
        }
    }

    fun getSearch(query: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(tsdbRepository
                        .doRequest(TSDBRest.teamSearch(query!!)),
                        TeamResponse::class.java)
            }

            val dataTeam = data.await().teams
            if(dataTeam != null) {
                view.hideLoading()
                view.showTeamList(dataTeam)
            } else {
                view.searchEmpty()
            }
        }
    }
}
