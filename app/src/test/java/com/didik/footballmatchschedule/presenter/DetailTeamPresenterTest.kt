package com.didik.footballmatchschedule.presenter

import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.api.TSDBRest
import com.didik.footballmatchschedule.model.Team
import com.didik.footballmatchschedule.model.TeamResponse
import com.didik.footballmatchschedule.utils.TestContextProvider
import com.didik.footballmatchschedule.view.detail.DetailTeamView
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailTeamPresenterTest {

    // membuat mock object sebagai parameter kelas DetailTeamPresenter
    @Mock
    lateinit var view: DetailTeamView

    @Mock
    lateinit var tsdbRepository: TSDBRepository

    @Mock
    lateinit var gson: Gson

    private lateinit var presenter: DetailTeamPresenter

    // dijalankan sebelum semua fungsi dijalankan
    @Before
    fun setUp() {
        // inisialisasi semua mock object
        MockitoAnnotations.initMocks(this)
        //inisialisasi presenter
        presenter = DetailTeamPresenter(view, tsdbRepository, gson, TestContextProvider())
    }

    /* melakukan pengujian detail team dengan id = 4328 */
    @Test
    fun getDetail() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val id = "4328"

        Mockito.`when`(gson.fromJson(tsdbRepository
                .doRequest(TSDBRest.teamDetail(id)),
                TeamResponse::class.java)
        ).thenReturn(response)

        presenter.getDetail(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamDetail(response.teams)
        Mockito.verify(view).hideLoading()
    }
}