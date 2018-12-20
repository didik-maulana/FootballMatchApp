package com.didik.footballmatchschedule.view.detail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.didik.footballmatchschedule.R
import com.didik.footballmatchschedule.adapter.PagerAdapter
import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.model.Team
import com.didik.footballmatchschedule.presenter.DetailTeamPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team.*
import org.jetbrains.anko.ctx

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {

    private lateinit var teamPresenter: DetailTeamPresenter

    private lateinit var team: Team
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        team = intent.getParcelableExtra("team")

        teamPresenter = DetailTeamPresenter(this, TSDBRepository(), Gson())
        teamPresenter.getDetail(team.idTeam!!)
        isFavorite = teamPresenter.isFavorite(ctx, team)

        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vpager.adapter = PagerAdapter(supportFragmentManager,
                mapOf(
                        getString(R.string.overview) to OverviewFragment.newInstance(team.strDescriptionEN.toString()),
                        getString(R.string.players) to PlayersFragment.newInstance(team.strTeam.toString())
                )
        )
        tab_layout.setupWithViewPager(vpager)

        team_name.text = team.strTeam
        team_informed.text = team.intFormedYear
        team_stadium.text = team.strStadium
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
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
            R.id.add_fav -> {
                if (isFavorite) {
                    teamPresenter.deleteFavorite(ctx, team)
                    Toast.makeText(this, "Delete your favorite", Toast.LENGTH_SHORT).show()
                } else {
                    teamPresenter.addToFavorite(ctx, team)
                    Toast.makeText(this, "Your favorite team", Toast.LENGTH_SHORT).show()
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

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showTeamDetail(detailTeam: List<Team>) {
        Picasso.get().load(detailTeam[0].strTeamBadge).into(team_badge)
    }
}
