package com.didik.footballmatchschedule.presenter

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.api.TSDBRest
import com.didik.footballmatchschedule.database.database
import com.didik.footballmatchschedule.model.Event
import com.didik.footballmatchschedule.model.TeamResponse
import com.didik.footballmatchschedule.utils.CoroutineContextProvider
import com.didik.footballmatchschedule.view.detail.DetailView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailEventPresenter(private val view: DetailView,
                           private val tsdbRepository: TSDBRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetail(idHome: String, idAway: String) {
        view.showLoading()

        async(context.main) {
            val dataHome = bg {
                gson.fromJson(tsdbRepository
                        .doRequest(TSDBRest.teamDetail(idHome)),
                        TeamResponse::class.java)
            }
            val dataAway = bg {
                gson.fromJson(tsdbRepository
                        .doRequest(TSDBRest.teamDetail(idAway)),
                        TeamResponse::class.java)
            }

            view.showEventDetails(dataHome.await().teams, dataAway.await().teams)
            view.hideLoading()
        }
    }

    fun addToFavorite(context: Context, data: Event) {
        try {
            context.database.use {
                insert(Event.TABLE_FAVORITE,
                        Event.ID_EVENT to data.idEvent,
                        Event.DATE_EVENT to data.dateEvent,
                        Event.DATE to data.strDate,
                        Event.TIME to data.strTime,
                        Event.HOME_ID to data.idHomeTeam,
                        Event.HOME_TEAM to data.strHomeTeam,
                        Event.HOME_SCORE to data.intHomeScore,
                        Event.HOME_FORMATION to data.strHomeFormation,
                        Event.HOME_GOAL_DETAILS to data.strHomeGoalDetails,
                        Event.HOME_SHOTS to data.intHomeShots,
                        Event.HOME_LINEUP_GOALKEEPER to data.strHomeLineupGoalkeeper,
                        Event.HOME_LINEUP_DEFENSE to data.strHomeLineupDefense,
                        Event.HOME_LINEUP_MIDFIELD to data.strHomeLineupMidfield,
                        Event.HOME_LINEUP_FORWARD to data.strHomeLineupForward,
                        Event.HOME_LINEUP_SUBSTITUTES to data.strHomeLineupSubstitutes,
                        Event.AWAY_ID to data.idAwayTeam,
                        Event.AWAY_TEAM to data.strAwayTeam,
                        Event.AWAY_SCORE to data.intAwayScore,
                        Event.AWAY_FORMATION to data.strAwayFormation,
                        Event.AWAY_GOAL_DETAILS to data.strAwayGoalDetails,
                        Event.AWAY_SHOTS to data.intAwayShots,
                        Event.AWAY_LINEUP_GOALKEEPER to data.strAwayLineupGoalkeeper,
                        Event.AWAY_LINEUP_DEFENSE to data.strAwayLineupDefense,
                        Event.AWAY_LINEUP_MIDFIELD to data.strAwayLineupMidfield,
                        Event.AWAY_LINEUP_FORWARD to data.strAwayLineupForward,
                        Event.AWAY_LINEUP_SUBSTITUTES to data.strAwayLineupSubstitutes)
            }
        } catch (e: SQLiteConstraintException) {
            Log.e("Problem", "${e.message}}")
        }
    }

    fun deleteFavorite(context: Context, data: Event) {
        try {
            context.database.use {
                delete(Event.TABLE_FAVORITE,
                        Event.ID_EVENT + " = {id}",
                        "id" to data.idEvent.toString())
            }
        } catch (e: SQLiteConstraintException) {
            Log.e("Problem", "${e.message}}")
        }
    }

    fun isFavorite(context: Context, data: Event): Boolean {
        var status = false
        context.database.use {
            val favorite = select(Event.TABLE_FAVORITE)
                    .whereArgs(Event.ID_EVENT + " = {id}",
                            "id" to data.idEvent.toString())
                    .parseList(classParser<Event>())
            status = !favorite.isEmpty()
        }
        return status
    }
}