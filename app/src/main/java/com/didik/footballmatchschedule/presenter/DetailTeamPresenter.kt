package com.didik.footballmatchschedule.presenter

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.api.TSDBRest
import com.didik.footballmatchschedule.database.database
import com.didik.footballmatchschedule.model.Team
import com.didik.footballmatchschedule.model.TeamResponse
import com.didik.footballmatchschedule.utils.CoroutineContextProvider
import com.didik.footballmatchschedule.view.detail.DetailTeamView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailTeamPresenter(private val view: DetailTeamView,
                          private val tsdbRepository: TSDBRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetail(id: String) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(tsdbRepository
                        .doRequest(TSDBRest.teamDetail(id)),
                        TeamResponse::class.java)
            }

            view.showTeamDetail(data.await().teams)
            view.hideLoading()
        }
    }

    fun addToFavorite(context: Context, data: Team) {
        try {
            context.database.use {
                insert(Team.TABLE_FAVORITE,
                        Team.ID_TEAM to data.idTeam,
                        Team.TEAM to data.strTeam,
                        Team.TEAM_BADGE to data.strTeamBadge,
                        Team.TEAM_JERSEY to data.strTeamBadge,
                        Team.FORMED_YEAR to data.intFormedYear,
                        Team.MANAGER to data.strManager,
                        Team.STADIUM to data.strStadium,
                        Team.STADIUM_THUMB to data.strStadiumThumb,
                        Team.DESCRIPTION to data.strDescriptionEN)
            }
        } catch (e: SQLiteConstraintException) {
            Log.e("Problem", "${e.message}}")
        }
    }

    fun deleteFavorite(context: Context, data: Team) {
        try {
            context.database.use {
                delete(Team.TABLE_FAVORITE,
                        Team.ID_TEAM + " = {id}",
                        "id" to data.idTeam.toString())
            }
        } catch (e: SQLiteConstraintException) {
            Log.e("Problem", "${e.message}}")
        }
    }

    fun isFavorite(context: Context, data: Team): Boolean {
        var status = false
        context.database.use {
            val favorite = select(Team.TABLE_FAVORITE)
                    .whereArgs(Team.ID_TEAM + " = {id}",
                            "id" to data.idTeam.toString())
                    .parseList(classParser<Team>())
            status = !favorite.isEmpty()
        }
        return status
    }
}