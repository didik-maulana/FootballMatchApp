package com.didik.footballmatchschedule.api

import java.net.URL

class TSDBRepository {

    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}
