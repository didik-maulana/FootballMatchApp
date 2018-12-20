package com.didik.footballmatchschedule.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.didik.footballmatchschedule.model.Event
import com.didik.footballmatchschedule.model.Team
import org.jetbrains.anko.db.*

class FavoriteOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "favorite.db", null, 1) {
    companion object {
        private var instance: FavoriteOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): FavoriteOpenHelper {
            if (instance == null) {
                instance = FavoriteOpenHelper(ctx.applicationContext)
            }
            return instance as FavoriteOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Event.TABLE_FAVORITE, true,
                Event.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Event.ID_EVENT to TEXT + UNIQUE,
                Event.DATE_EVENT to TEXT,
                Event.DATE to TEXT,
                Event.TIME to TEXT,
                Event.HOME_ID to TEXT,
                Event.HOME_TEAM to TEXT,
                Event.HOME_SCORE to TEXT,
                Event.HOME_FORMATION to TEXT,
                Event.HOME_GOAL_DETAILS to TEXT,
                Event.HOME_SHOTS to TEXT,
                Event.HOME_LINEUP_GOALKEEPER to TEXT,
                Event.HOME_LINEUP_DEFENSE to TEXT,
                Event.HOME_LINEUP_MIDFIELD to TEXT,
                Event.HOME_LINEUP_FORWARD to TEXT,
                Event.HOME_LINEUP_SUBSTITUTES to TEXT,
                Event.AWAY_ID to TEXT,
                Event.AWAY_TEAM to TEXT,
                Event.AWAY_SCORE to TEXT,
                Event.AWAY_FORMATION to TEXT,
                Event.AWAY_GOAL_DETAILS to TEXT,
                Event.AWAY_SHOTS to TEXT,
                Event.AWAY_LINEUP_GOALKEEPER to TEXT,
                Event.AWAY_LINEUP_DEFENSE to TEXT,
                Event.AWAY_LINEUP_MIDFIELD to TEXT,
                Event.AWAY_LINEUP_FORWARD to TEXT,
                Event.AWAY_LINEUP_SUBSTITUTES to TEXT)

        db.createTable(Team.TABLE_FAVORITE, true,
                Team.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Team.ID_TEAM to TEXT + UNIQUE,
                Team.TEAM to TEXT,
                Team.TEAM_BADGE to TEXT,
                Team.TEAM_JERSEY to TEXT,
                Team.FORMED_YEAR to TEXT,
                Team.MANAGER to TEXT,
                Team.STADIUM to TEXT,
                Team.STADIUM_THUMB to TEXT,
                Team.DESCRIPTION to TEXT)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Event.TABLE_FAVORITE, true)
        db.dropTable(Team.TABLE_FAVORITE, true)
    }
}

val Context.database: FavoriteOpenHelper
    get() = FavoriteOpenHelper.getInstance(applicationContext)