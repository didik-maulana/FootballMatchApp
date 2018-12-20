package com.didik.footballmatchschedule.view.team

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
import com.didik.footballmatchschedule.adapter.TeamAdapter
import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.model.League
import com.didik.footballmatchschedule.model.LeagueResponse
import com.didik.footballmatchschedule.model.Team
import com.didik.footballmatchschedule.presenter.TeamPresenter
import com.didik.footballmatchschedule.utils.gone
import com.didik.footballmatchschedule.utils.invisible
import com.didik.footballmatchschedule.utils.visible
import com.didik.footballmatchschedule.view.detail.DetailTeamActivity
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TabTeam : Fragment(), TeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var league: League

    private lateinit var listTeam: RecyclerView
    private lateinit var emptyData: LinearLayout
    private lateinit var emptyTeam: TextView

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

                            emptyTeam = textView {
                                id = R.id.empty_team
                                gravity = Gravity.CENTER
                                padding = dip(8)
                                text = "Empty favorites team"
                            }
                        }.lparams {
                            centerInParent()
                        }

                        listTeam = recyclerView {
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
                                add(0, R.id.menu_last, 0, "TEAMS")
                                        .setIcon(R.drawable.ic_team)
                                        .setOnMenuItemClickListener {
                                            presenter.getTeam(league.idLeague!!)
                                            setHasOptionsMenu(true)
                                            false
                                        }

                                add(0, R.id.menu_next, 0, "FAVORITES")
                                        .setIcon(R.drawable.fav_enable)
                                        .setOnMenuItemClickListener {
                                            setHasOptionsMenu(false)
                                            presenter.getFavorite(ctx)
                                            false
                                        }
                            }
                        }.lparams(matchParent, wrapContent) {
                            alignParentBottom()
                        }
                    }
                }
            }

            adapter = TeamAdapter(teams) {
                startActivity<DetailTeamActivity>("team" to it)
            }
            listTeam.adapter = adapter
            val request = TSDBRepository()
            val gson = Gson()
            presenter = TeamPresenter(this@TabTeam, request, gson)
            presenter.getLeague()
            setHasOptionsMenu(true)

            swipeRefresh.onRefresh {
                if(presenter.menu == 1) {
                    league = spinner.selectedItem as League
                    presenter.getTeam(league.idLeague!!)
                } else {
                    presenter.getFavorite(ctx)
                }
            }

        }.view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_search_view, menu)

        val menuSearch = menu?.findItem(R.id.menu_searchview)
        val searchView = menuSearch?.actionView as SearchView

        searchQuery(searchView)
    }

    private fun searchQuery(data: SearchView) {
        data.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getSearch(query.toString())
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.toString().isEmpty()) {
                    spinnerLayout.visible()
                    league = spinner.selectedItem as League
                    presenter.getTeam(league.idLeague!!)
                } else {
                    spinnerLayout.gone()
                    presenter.getSearch(query.toString())
                }
                return true
            }
        })
    }

    override fun showLeague(data: LeagueResponse) {
        spinner.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, data.countrys)
        spinner.setSelection(40)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinner.selectedItem as League

                presenter.getTeam(league.idLeague!!)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (presenter.menu == 2)
            presenter.getFavorite(ctx)
    }

    override fun hideLoading() {
        progressBar.invisible()
        listTeam.visible()
    }

    override fun showLoading() {
        progressBar.visible()
        listTeam.invisible()
        emptyData.invisible()

        if (presenter.menu == 2) {
            spinnerLayout.gone()
        } else {
            spinnerLayout.visible()
        }
    }

    override fun showTeamList(data: List<Team>) {
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
        swipeRefresh.isRefreshing = false
    }

    override fun showEmptyTeam() {
        progressBar.invisible()
        listTeam.invisible()
        emptyData.visible()
        emptyTeam.text = "Empty favorites team"
    }

    override fun searchEmpty() {
        progressBar.invisible()
        listTeam.invisible()
        emptyData.visible()
        emptyTeam.text = "Team not found"
    }
}