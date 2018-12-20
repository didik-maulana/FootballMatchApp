package com.didik.footballmatchschedule.api

import com.didik.footballmatchschedule.BuildConfig

object TSDBRest {

    fun leagueAll(): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/search_all_leagues.php?s=Soccer"
    }

    fun eventPast(id: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/eventspastleague.php?id=" + id
    }

    fun eventNext(id: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/eventsnextleague.php?id=" + id
    }

    fun eventSearch(event: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/searchevents.php?e=" + event
    }

    fun teamDetail(id: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/lookupteam.php?id=" + id
    }

    fun teamAll(league: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/lookup_all_teams.php?id=" + league
    }

    fun teamSearch(team: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/searchteams.php?t=" + team
    }


    fun playerTeam(team: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/searchplayers.php?t=" + team
    }
}