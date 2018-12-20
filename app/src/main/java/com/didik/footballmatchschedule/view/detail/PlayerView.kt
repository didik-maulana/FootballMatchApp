package com.didik.footballmatchschedule.view.detail

import com.didik.footballmatchschedule.model.Player

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayer(data: List<Player>)
}
