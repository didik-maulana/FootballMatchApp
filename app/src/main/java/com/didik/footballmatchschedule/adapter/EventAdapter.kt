package com.didik.footballmatchschedule.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.didik.footballmatchschedule.R
import com.didik.footballmatchschedule.model.Event
import com.didik.footballmatchschedule.utils.DateFormat
import org.jetbrains.anko.*

class EventAdapter(private val events: List<Event>,
                   private val listener: (Event) -> Unit) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(EventUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

    override fun getItemCount(): Int = events.size

    class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val eventDate: TextView = view.findViewById(R.id.date)
        private val eventTime: TextView = view.findViewById(R.id.time)
        private val homeTeam: TextView = view.findViewById(R.id.home_team)
        private val homeScore: TextView = view.findViewById(R.id.home_score)
        private val awayTeam: TextView = view.findViewById(R.id.away_team)
        private val awayScore: TextView = view.findViewById(R.id.away_score)

        fun bindItem(event: Event, listener: (Event) -> Unit) {
            eventDate.text = DateFormat.dateFormat(event.dateEvent)
            eventTime.text = DateFormat.timeFormat(event.strTime)
            homeTeam.text = event.strHomeTeam
            homeScore.text = event.intHomeScore
            awayTeam.text = event.strAwayTeam
            awayScore.text = event.intAwayScore

            itemView.setOnClickListener {
                listener(event)
            }
        }
    }
}

class EventUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(matchParent, wrapContent)
                orientation = LinearLayout.VERTICAL

                linearLayout {
                    backgroundColor = Color.WHITE
                    orientation = LinearLayout.VERTICAL
                    padding = dip(8)

                    textView {
                        id = R.id.date
                        textSize = 12f
                        textColor = ContextCompat.getColor(ctx, R.color.colorDate)
                        gravity = Gravity.CENTER
                    }.lparams(matchParent, wrapContent)

                    textView {
                        id = R.id.time
                        textSize = 12f
                        textColor = ContextCompat.getColor(ctx, R.color.colorDate)
                        gravity = Gravity.CENTER
                    }.lparams(matchParent, wrapContent)

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        textView {
                            id = R.id.home_team
                            gravity = Gravity.CENTER
                            textSize = 14f
                            text = "home team"
                        }.lparams(matchParent, wrapContent, 1f)

                        linearLayout {
                            gravity = Gravity.CENTER_VERTICAL

                            textView {
                                id = R.id.home_score
                                padding = dip(8)
                                textSize = 18f
                                setTypeface(null, Typeface.BOLD)
                            }

                            textView {
                                text = "vs"
                            }

                            textView {
                                id = R.id.away_score
                                padding = dip(8)
                                textSize = 18f
                                setTypeface(null, Typeface.BOLD)
                            }
                        }

                        textView {
                            id = R.id.away_team
                            gravity = Gravity.CENTER
                            textSize = 14f
                            text = "away_team"
                        }.lparams(matchParent, wrapContent, 1f)
                    }
                }.lparams(matchParent, matchParent) {
                    setMargins(dip(16), dip(8), dip(16), dip(8))
                }
            }
        }
    }
}

