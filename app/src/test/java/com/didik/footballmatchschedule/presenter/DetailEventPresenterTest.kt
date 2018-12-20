package com.didik.footballmatchschedule.presenter

import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.api.TSDBRest
import com.didik.footballmatchschedule.model.TeamResponse
import com.didik.footballmatchschedule.model.Team
import com.didik.footballmatchschedule.utils.TestContextProvider
import com.didik.footballmatchschedule.view.detail.DetailView
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailEventPresenterTest {

    // membuat mock object sebagai parameter kelas DetailEventPresenter
    @Mock
    lateinit var view: DetailView

    @Mock
    lateinit var tsdbRepository: TSDBRepository

    @Mock
    lateinit var gson: Gson

    private lateinit var eventPresenter: DetailEventPresenter

    // dijalankan sebelum semua fungsi dijalankan
    @Before
    fun setUp() {
        // inisialisasi semua mock object
        MockitoAnnotations.initMocks(this)
        //inisialisasi eventPresenter
        eventPresenter = DetailEventPresenter(view, tsdbRepository, gson, TestContextProvider())
    }

    /* melakukan pengujian detail event dengan id = 4328 */
    @Test
    fun getDetail() {
        val events: MutableList<Team> = mutableListOf()
        val response = TeamResponse(events)
        val id = "4328"

        Mockito.`when`(gson.fromJson(tsdbRepository
                .doRequest(TSDBRest.teamDetail(id)),
                TeamResponse::class.java)
        ).thenReturn(response)

        eventPresenter.getDetail(id, id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showEventDetails(response.teams, response.teams)
        Mockito.verify(view).hideLoading()
    }
}