package com.didik.footballmatchschedule.adapter

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.didik.footballmatchschedule.R
import com.didik.footballmatchschedule.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class PlayerAdapter(private val players: List<Player>,
                    private val clickListener: (Player) -> Unit) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PlayerUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(players[position], clickListener)
    }

    override fun getItemCount(): Int = players.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Deklarasi variabel
        private val playerImage: ImageView = view.findViewById(R.id.player_image)
        private val playerName: TextView = view.findViewById(R.id.player_name)
        private val playerPosition: TextView = view.findViewById(R.id.player_position)

        fun bindItem(player: Player, clickListener: (Player) -> Unit) {

            if(player.strCutout != null) {
                Picasso.get().load(player.strCutout).into(playerImage)
            } else {
                Picasso.get().load(R.drawable.ic_launcher_background).into(playerImage)
            }

            playerName.text = player.strPlayer
            playerPosition.text = player.strPosition

            itemView.setOnClickListener { clickListener(player) }
        }
    }
}

// Anko Layout
class PlayerUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(matchParent, wrapContent)
                orientation = LinearLayout.VERTICAL

                linearLayout {
                    gravity = Gravity.CENTER_VERTICAL
                    padding = dip(18)

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        imageView {
                            id = R.id.player_image
                        }.lparams(dip(60), dip(60))

                        textView {
                            id = R.id.player_name
                        }.lparams(wrapContent, wrapContent) {
                            leftMargin = dip(12)
                        }
                    }.lparams(matchParent, wrapContent, 1f)

                    textView {
                        id = R.id.player_position
                        setTypeface(null, Typeface.BOLD)
                        gravity = Gravity.END
                    }.lparams(matchParent, wrapContent, 1f)
                }
            }
        }
    }
}
