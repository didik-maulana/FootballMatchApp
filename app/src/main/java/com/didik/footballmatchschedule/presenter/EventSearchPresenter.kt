package com.didik.footballmatchschedule.presenter

import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.api.TSDBRest
import com.didik.footballmatchschedule.model.EventSearchResponse
import com.didik.footballmatchschedule.utils.CoroutineContextProvider
import com.didik.footballmatchschedule.view.event.EventSearchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class EventSearchPresenter(private val view: EventSearchView,
                           private val tsdbRepository: TSDBRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getSearch(query: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(tsdbRepository
                        .doRequest(TSDBRest.eventSearch(query!!)),
                        EventSearchResponse::class.java)
            }

            val dataEvent = data.await().event
            if(dataEvent != null) {
                view.showEventList(dataEvent)
                view.hideLoading()
            } else {
                view.showEmptyEvent()
            }
        }
    }
}
