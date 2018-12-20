package com.didik.footballmatchschedule.presenter

import android.content.Context
import android.widget.Toast
import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.api.TSDBRest
import com.didik.footballmatchschedule.database.database
import com.didik.footballmatchschedule.model.Event
import com.didik.footballmatchschedule.model.EventResponse
import com.didik.footballmatchschedule.model.LeagueResponse
import com.didik.footballmatchschedule.utils.CoroutineContextProvider
import com.didik.footballmatchschedule.view.event.EventPresentation
import com.didik.footballmatchschedule.view.event.EventView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class EventPresenter(private val view: EventView,
                     private val tsdbRepository: TSDBRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()):
        EventPresentation() {

    var menu = 1

    override fun getLeague() {
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

    override fun getPastEventList(leagueId: String) {
        menu = 1
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(tsdbRepository
                        .doRequest(TSDBRest.eventPast(leagueId)),
                        EventResponse::class.java)
            }


            view.showEventList(data.await().events)
            view.hideLoading()
        }
    }

    override fun getNextEventList(leagueId: String) {
        menu = 2
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(tsdbRepository
                        .doRequest(TSDBRest.eventNext(leagueId)),
                        EventResponse::class.java)
            }


            view.showEventList(data.await().events)
            view.hideLoading()
        }
    }

    override fun getFavEventList(context: Context) {
        menu = 3
        view.showLoading()
        val data: MutableList<Event> = mutableListOf()
        context.database.use {
            val favorite = select(Event.TABLE_FAVORITE)
                    .parseList(classParser<Event>())
            data.addAll(favorite)
        }
        view.hideLoading()

        if(data.size > 0) {
            view.showEventList(data)
        } else {
            view.showEmptyEvent()
        }
    }
}
