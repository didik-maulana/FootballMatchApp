package com.didik.footballmatchschedule.presenter

import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.api.TSDBRest
import com.didik.footballmatchschedule.model.Event
import com.didik.footballmatchschedule.model.EventSearchResponse
import com.didik.footballmatchschedule.utils.TestContextProvider
import com.didik.footballmatchschedule.view.event.EventSearchView
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EventSearchPresenterTest {

    // membuat mock object sebagai parameter kelas EventSeacrhPresenter
    @Mock
    private lateinit var view: EventSearchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var tsdbRepository: TSDBRepository

    private lateinit var presenter: EventSearchPresenter

    // dijalankan sebelum semua fungsi dijalankan
    @Before
    fun setUp() {
        // inisialisasi semua mock object
        MockitoAnnotations.initMocks(this)
        //inisialisasi presenter
        presenter = EventSearchPresenter(view, tsdbRepository, gson, TestContextProvider())
    }

    @Test
    fun getSearch() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventSearchResponse(events)
        val query = "Real Madrid"

        Mockito.`when`(gson.fromJson(tsdbRepository
                .doRequest(TSDBRest.eventSearch(query)),
                EventSearchResponse::class.java)
        ).thenReturn(response)

        presenter.getSearch(query)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showEventList(response.event)
        Mockito.verify(view).hideLoading()
    }
}