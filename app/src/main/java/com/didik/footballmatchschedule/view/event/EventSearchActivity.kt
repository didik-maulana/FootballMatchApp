package com.didik.footballmatchschedule.view.event

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.Menu
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SearchView
import com.didik.footballmatchschedule.R
import com.didik.footballmatchschedule.R.color.colorAccent
import com.didik.footballmatchschedule.adapter.EventAdapter
import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.model.Event
import com.didik.footballmatchschedule.presenter.EventSearchPresenter
import com.didik.footballmatchschedule.utils.invisible
import com.didik.footballmatchschedule.utils.visible
import com.didik.footballmatchschedule.view.detail.DetailActivity
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class EventSearchActivity : AppCompatActivity(), EventSearchView {

    private var event: MutableList<Event> = mutableListOf()
    private lateinit var presenter: EventSearchPresenter
    private lateinit var adapter: EventAdapter

    private lateinit var listEvent: RecyclerView
    private lateinit var emptyData: LinearLayout

    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layoutSearch()
        adapter = EventAdapter(event) {
            startActivity<DetailActivity>("event" to it)
        }

        listEvent.adapter = adapter
        val request = TSDBRepository()
        val gson = Gson()
        presenter = EventSearchPresenter(this@EventSearchActivity, request, gson)
        presenter.getSearch("")

    }

    private fun layoutSearch() {
        linearLayout {
            orientation = LinearLayout.VERTICAL

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
                            text = "Event not found"
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
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_view, menu)

        val menuSearch = menu?.findItem(R.id.menu_searchview)
        val searchView = menuSearch?.actionView as SearchView
        searchView.isIconified = false

        searchQuery(searchView)
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchQuery(data: SearchView) {
        data.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getSearch(query.toString())
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                presenter.getSearch(query.toString())
                return true
            }
        })
    }

    override fun hideLoading() {
        progressBar.invisible()
        listEvent.visible()
    }

    override fun showLoading() {
        listEvent.invisible()
        emptyData.invisible()
    }

    override fun showEventList(data: List<Event>) {
        event.clear()
        event.addAll(data)
        adapter.notifyDataSetChanged()
        swipeRefresh.isRefreshing = false
    }

    override fun showEmptyEvent() {
        progressBar.invisible()
        listEvent.invisible()
        emptyData.visible()
    }
}