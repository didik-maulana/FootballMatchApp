package com.didik.footballmatchschedule.presenter

import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.api.TSDBRest
import com.didik.footballmatchschedule.model.PlayerResponse
import com.didik.footballmatchschedule.utils.CoroutineContextProvider
import com.didik.footballmatchschedule.view.detail.PlayerView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(private val view: PlayerView,
                      private val tsdbRepository: TSDBRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayer(team: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(tsdbRepository
                        .doRequest(TSDBRest.playerTeam(team)),
                        PlayerResponse::class.java)
            }
            view.hideLoading()
            view.showPlayer(data.await().player)
        }
    }
}