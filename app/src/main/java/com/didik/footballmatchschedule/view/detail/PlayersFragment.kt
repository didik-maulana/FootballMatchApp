package com.didik.footballmatchschedule.view.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.didik.footballmatchschedule.R
import com.didik.footballmatchschedule.adapter.PlayerAdapter
import com.didik.footballmatchschedule.api.TSDBRepository
import com.didik.footballmatchschedule.model.Player
import com.didik.footballmatchschedule.presenter.PlayerPresenter
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI

class PlayersFragment : Fragment(), PlayerView {

    private var players: MutableList<Player> = mutableListOf()
    private lateinit var adapter: PlayerAdapter
    private lateinit var presenter: PlayerPresenter

    private lateinit var listPlayer: RecyclerView

    companion object {
        fun newInstance(args: String): PlayersFragment {
            val fragment = PlayersFragment()
            fragment.arguments = bundleOf("player" to args)

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                orientation = LinearLayout.VERTICAL

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listPlayer = recyclerView {
                        id = R.id.list_player
                        layoutManager = LinearLayoutManager(ctx)
                    }.lparams(matchParent, matchParent) {

                    }
                }
            }

            adapter = PlayerAdapter(players) {
                startActivity<DetailPlayerActivity>("player" to it)
            }

            listPlayer.adapter = adapter
            val request = TSDBRepository()
            val gson = Gson()
            presenter = PlayerPresenter(this@PlayersFragment, request, gson)
            presenter.getPlayer(arguments?.getString("player")!!)

        }.view
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showPlayer(data: List<Player>) {
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }
}