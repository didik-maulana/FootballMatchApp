package com.didik.footballmatchschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.didik.footballmatchschedule.R
import com.didik.footballmatchschedule.model.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class TeamAdapter(private val teams: List<Team>,
                  private val clickListener: (Team) -> Unit) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], clickListener)
    }

    override fun getItemCount(): Int = teams.size

    inner class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Deklarasi variabel
        private val teamBadge: ImageView = view.findViewById(R.id.team_badge)
        private val teamName: TextView = view.findViewById(R.id.team_name)

        fun bindItem(teams: Team, clickListener: (Team) -> Unit) {
            Picasso.get().load(teams.strTeamBadge).into(teamBadge)
            teamName.text = teams.strTeam

            itemView.setOnClickListener { clickListener(teams) }
        }
    }
}

// Anko Layout
class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.team_badge
                }.lparams {
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.team_name
                    textSize = 16f
                }.lparams { margin = dip(15) }
            }
        }
    }
}
