package com.didik.footballmatchschedule.view.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ScrollView
import com.didik.footballmatchschedule.R
import com.didik.footballmatchschedule.model.Player
import com.didik.footballmatchschedule.utils.invisible
import com.didik.footballmatchschedule.utils.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player.*

class DetailPlayerActivity : AppCompatActivity(), PlayerView {

    private lateinit var scrollView: ScrollView

    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        player = intent.getParcelableExtra("player")
        layoutDetail(player)

        supportActionBar?.title = "Player Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun layoutDetail(player: Player) {
        player_name.text = player.strPlayer
        player_weight.text = player.strWeight
        player_height.text = player.strHeight
        player_position.text = player.strPosition
        player_nationality.text = player.strNationality
        player_desc.text = player.strDescriptionEN

        if(player.strCutout != null) {
            Picasso.get().load(player.strCutout).into(player_img)
        } else {
            Picasso.get().load(R.drawable.ic_launcher_background).into(player_img)
        }
    }

    override fun showLoading() {
        scrollView.invisible()
    }

    override fun hideLoading() {
        scrollView.visible()
    }

    override fun showPlayer(data: List<Player>) {

    }
}