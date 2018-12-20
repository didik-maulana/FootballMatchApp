package com.didik.footballmatchschedule.view.detail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Toast
import com.didik.footballmatchschedule.R
import com.didik.footballmatchschedule.R.id.add_fav
import com.didik.footballmatchschedule.R.menu.detail_menu
import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.model.Event
import com.didik.footballmatchschedule.model.Team
import com.didik.footballmatchschedule.presenter.DetailEventPresenter
import com.didik.footballmatchschedule.utils.DateFormat
import com.didik.footballmatchschedule.utils.invisible
import com.didik.footballmatchschedule.utils.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.find

class DetailActivity : AppCompatActivity(), DetailView {

    private lateinit var eventPresenter: DetailEventPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var scrollView: ScrollView

    private lateinit var event: Event
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        event = intent.getParcelableExtra("event")
        layoutDetail(event)

        progressBar = find(R.id.progress_bar)
        scrollView = find(R.id.scrollview)

        eventPresenter = DetailEventPresenter(this, TSDBRepository(), Gson())
        eventPresenter.getDetail(event.idHomeTeam!!, event.idAwayTeam!!)
        isFavorite = eventPresenter.isFavorite(ctx, event)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFav()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_fav -> {
                if (isFavorite) {
                    eventPresenter.deleteFavorite(ctx, event)
                    Toast.makeText(this, "Delete your favorite", Toast.LENGTH_SHORT).show()
                } else {
                    eventPresenter.addToFavorite(ctx, event)
                    Toast.makeText(this, "Your favorite event", Toast.LENGTH_SHORT).show()
                }

                isFavorite = !isFavorite
                setFav()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFav() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.fav_enable)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.fav_disable)
        }
    }

    private fun layoutDetail(event: Event) {

        event_date.text = DateFormat.dateFormat(event.dateEvent)
        event_time.text = DateFormat.timeFormat(event.strTime)

        // Home
        home_team.text = event.strHomeTeam
        home_formation.text = event.strHomeFormation
        home_score.text = event.intHomeScore
        home_goal.text = event.strHomeGoalDetails
        home_shot.text = event.intHomeShots
        home_gk.text = event.strHomeLineupGoalkeeper
        home_df.text = event.strHomeLineupDefense
        home_md.text = event.strHomeLineupMidfield
        home_fw.text = event.strHomeLineupForward
        home_sb.text = event.strHomeLineupSubstitutes

        //Away
        away_team.text = event.strAwayTeam
        away_formation.text = event.strHomeFormation
        away_score.text = event.intAwayScore
        away_goal.text = event.strAwayGoalDetails
        away_shot.text = event.intAwayShots
        away_gk.text = event.strAwayLineupGoalkeeper
        away_df.text = event.strAwayLineupDefense
        away_md.text = event.strAwayLineupMidfield
        away_fw.text = event.strAwayLineupForward
        away_sb.text = event.strAwayLineupSubstitutes
    }

    override fun showLoading() {
        progressBar.visible()
        scrollView.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        scrollView.visible()
    }

    override fun showEventDetails(detailHome: List<Team>, detailAway: List<Team>) {
        Picasso.get().load(detailHome[0].strTeamBadge).into(logo_home)
        Picasso.get().load(detailAway[0].strTeamBadge).into(logo_away)
    }
}
