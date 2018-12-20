package com.didik.footballmatchschedule.view.event

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.didik.footballmatchschedule.R
import com.didik.footballmatchschedule.R.color.colorAccent
import com.didik.footballmatchschedule.adapter.EventAdapter
import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.model.Event
import com.didik.footballmatchschedule.model.League
import com.didik.footballmatchschedule.model.LeagueResponse
import com.didik.footballmatchschedule.presenter.EventPresenter
import com.didik.footballmatchschedule.utils.gone
import com.didik.footballmatchschedule.utils.invisible
import com.didik.footballmatchschedule.utils.visible
import com.didik.footballmatchschedule.view.detail.DetailActivity
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TabEvent : Fragment(), EventView {

    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: EventPresentation
    private lateinit var adapter: EventAdapter
    private lateinit var league: League

    private lateinit var listEvent: RecyclerView
    private lateinit var emptyData: LinearLayout

    private lateinit var spinnerLayout: LinearLayout
    private lateinit var spinner: Spinner
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                orientation = LinearLayout.VERTICAL

                spinnerLayout = linearLayout {
                    orientation = LinearLayout.VERTICAL

                    spinner = spinner {
                        id = R.id.spinner
                        minimumHeight = dip(50)
                    }
                }

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(colorAccent)

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        emptyData = linearLayout {
                            orientation = LinearLayout.VERTICAL
                            visibility = LinearLayout.INVISIBLE

                            textView {
                                gravity = Gravity.CENTER
                                padding = dip(8)
                                text = "Empty favorites event"
                            }
                        }.lparams {
                            centerInParent()
                        }

                        listEvent = recyclerView {
                            id = R.id.list_event
                            layoutManager = LinearLayoutManager(ctx)
                        }.lparams(matchParent, matchParent) {
                            topOf(R.id.menu_bnv)
                        }

                        progressBar = progressBar {
                        }.lparams {
                            centerInParent()
                        }

                        bottomNavigationView {
                            id = R.id.menu_bnv
                            backgroundColor = Color.WHITE

                            menu.apply {
                                add(0, R.id.menu_last, 0, "LAST")
                                        .setIcon(R.drawable.ic_eventpast)
                                        .setOnMenuItemClickListener {
                                            presenter.getPastEventList(league.idLeague!!)
                                            setHasOptionsMenu(true)
                                            false
                                        }

                                add(0, R.id.menu_next, 0, "NEXT")
                                        .setIcon(R.drawable.ic_event)
                                        .setOnMenuItemClickListener {
                                            presenter.getNextEventList(league.idLeague!!)
                                            setHasOptionsMenu(true)
                                            false
                                        }

                                add(0, R.id.menu_event_fav, 0, "FAVORITES")
                                        .setIcon(R.drawable.fav_enable)
                                        .setOnMenuItemClickListener {
                                            presenter.getFavEventList(ctx)
                                            setHasOptionsMenu(false)
                                            false
                                        }
                            }
                        }.lparams(matchParent, wrapContent) {
                            alignParentBottom()
                        }
                    }
                }
            }

            adapter = EventAdapter(events) {
                startActivity<DetailActivity>("event" to it)
            }

            listEvent.adapter = adapter
            val request = TSDBRepository()
            val gson = Gson()
            presenter = EventPresenter(this@TabEvent, request, gson)
            presenter.getLeague()

            setHasOptionsMenu(true)

            swipeRefresh.onRefresh {
                when {
                    (presenter as EventPresenter).menu == 1 -> presenter.getPastEventList(league.idLeague!!)
                    (presenter as EventPresenter).menu == 2 -> presenter.getNextEventList(league.idLeague!!)
                    else -> presenter.getFavEventList(ctx)
                }
            }
        }.view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_search -> {
                context?.startActivity<EventSearchActivity>()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLeague(data: LeagueResponse) {
        spinner.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, data.countrys)
        spinner.setSelection(40)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinner.selectedItem as League

                when ((presenter as EventPresenter).menu) {
                    1 -> presenter.getPastEventList(league.idLeague!!)
                    2 -> presenter.getNextEventList(league.idLeague!!)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if ((presenter as EventPresenter).menu == 3)
            presenter.getFavEventList(ctx)
    }

    override fun hideLoading() {
        progressBar.invisible()
        listEvent.visible()
    }

    override fun showLoading() {
        progressBar.visible()
        listEvent.invisible()
        emptyData.invisible()

        if ((presenter as EventPresenter).menu == 3) {
            spinnerLayout.gone()
        } else {
            spinnerLayout.visible()
        }
    }

    override fun showEventList(data: List<Event>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
        swipeRefresh.isRefreshing = false
    }

    override fun showEmptyEvent() {
        progressBar.invisible()
        listEvent.invisible()
        emptyData.visible()
    }
}