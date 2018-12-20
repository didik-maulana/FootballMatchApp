package com.didik.footballmatchschedule.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
        val id: Long?,
        val idTeam: String?,
        val strTeam: String?,
        val strTeamBadge: String?,
        val strTeamJersey: String?,
        val intFormedYear: String?,
        val strManager: String?,
        val strStadium: String?,
        val strStadiumThumb: String?,
        val strDescriptionEN: String?) : Parcelable {

    companion object {
        const val TABLE_FAVORITE = "TABLE_TEAMS"
        const val ID = "ID"
        const val ID_TEAM = "ID_TEAM"
        const val TEAM = "TEAM"
        const val TEAM_BADGE = "TEAM_BADGE"
        const val TEAM_JERSEY = "TEAM_JERSEY"
        const val FORMED_YEAR = "FORMED_YEAR"
        const val MANAGER = "MANAGER"
        const val STADIUM = "STADIUM"
        const val STADIUM_THUMB = "STADIUM_THUMB"
        const val DESCRIPTION = "DESCRIPTION"
    }

}