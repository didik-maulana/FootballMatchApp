package com.didik.footballmatchschedule.presenter

import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.api.TSDBRest
import com.didik.footballmatchschedule.model.Player
import com.didik.footballmatchschedule.model.PlayerResponse
import com.didik.footballmatchschedule.utils.TestContextProvider
import com.didik.footballmatchschedule.view.detail.PlayerView
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerPresenterTest {

    // membuat mock object sebagai parameter kelas PlayerPresenter
    @Mock
    private lateinit var view: PlayerView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var tsdbRepository: TSDBRepository

    private lateinit var presenter: PlayerPresenter

    // dijalankan sebelum semua fungsi dijalankan
    @Before
    fun setUp() {
        // inisialisasi semua mock object
        MockitoAnnotations.initMocks(this)
        //inisialisasi presenter
        presenter = PlayerPresenter(view, tsdbRepository, gson, TestContextProvider())
    }

    @Test
    fun getPlayer() {
        val player: MutableList<Player> = mutableListOf()
        val response = PlayerResponse(player)
        val team = "Real Madrid"

        Mockito.`when`(gson.fromJson(tsdbRepository
                .doRequest(TSDBRest.playerTeam(team)),
                PlayerResponse::class.java)
        ).thenReturn(response)

        presenter.getPlayer(team)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showPlayer(response.player)
        Mockito.verify(view).hideLoading()
    }
}