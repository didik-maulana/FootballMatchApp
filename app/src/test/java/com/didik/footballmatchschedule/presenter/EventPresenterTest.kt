package com.didik.footballmatchschedule.presenter

import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.api.TSDBRest
import com.didik.footballmatchschedule.model.Event
import com.didik.footballmatchschedule.model.EventResponse
import com.didik.footballmatchschedule.utils.TestContextProvider
import com.didik.footballmatchschedule.view.event.EventView
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class EventPresenterTest {

    // membuat mock object sebagai parameter kelas EventPresenter
    @Mock
    private lateinit var view: EventView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var tsdbRepository: TSDBRepository

    private lateinit var presenter: EventPresenter

    // dijalankan sebelum semua fungsi dijalankan
    @Before
    fun setUp() {
        // inisialisasi semua mock object
        MockitoAnnotations.initMocks(this)
        //inisialisasi presenter
        presenter = EventPresenter(view, tsdbRepository, gson, TestContextProvider())
    }

    /* melakukan pengujian past event dengan id league = 4328 */
    @Test
    fun getPastEventList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val id = "4328"

        `when`(gson.fromJson(tsdbRepository
                .doRequest(TSDBRest.eventPast(id)),
                EventResponse::class.java)
        ).thenReturn(response)

        presenter.getPastEventList(id)

        verify(view).showLoading()
        verify(view).showEventList(events)
        verify(view).hideLoading()
    }

    /* melakukan pengujian next event dengan id league = 4328 */
    @Test
    fun getEventNext() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val id = "4328"

        `when`(gson.fromJson(tsdbRepository
                .doRequest(TSDBRest.eventNext(id)),
                EventResponse::class.java)
        ).thenReturn(response)

        presenter.getNextEventList(id)

        verify(view).showLoading()
        verify(view).showEventList(events)
        verify(view).hideLoading()
    }
}