package com.didik.footballmatchschedule.presenter

import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.api.TSDBRest
import com.didik.footballmatchschedule.model.EventResponse
import com.didik.footballmatchschedule.model.Team
import com.didik.footballmatchschedule.model.TeamResponse
import com.didik.footballmatchschedule.utils.TestContextProvider
import com.didik.footballmatchschedule.view.team.TeamView
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TeamPresenterTest {

    @Mock
    private lateinit var view: TeamView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var tsdbRepository: TSDBRepository

    private lateinit var presenter: TeamPresenter

    // dijalankan sebelum semua fungsi dijalankan
    @Before
    fun setUp() {
        // inisialisasi semua mock object
        MockitoAnnotations.initMocks(this)
        //inisialisasi presenter
        presenter = TeamPresenter(view, tsdbRepository, gson, TestContextProvider())
    }

    @Test
    fun getLeague() {
    }

    @Test
    fun getTeam() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val league = "English Premier League"

        `when`(gson.fromJson(tsdbRepository
                .doRequest(TSDBRest.teamAll(league)),
                TeamResponse::class.java)
        ).thenReturn(response)

        presenter.getTeam(league)

        verify(view).showLoading()
        verify(view).showTeamList(teams)
        verify(view).hideLoading()
    }
}