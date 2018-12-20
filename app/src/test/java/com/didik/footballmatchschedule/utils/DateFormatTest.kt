package com.didik.footballmatchschedule.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class DateFormatTest {

    // melakukan unit test pada fungsi shortDate untuk mencocokan dengan tanggal
    @Test
    fun dateFormat() {
        assertEquals("Mon, 22 May 2000", DateFormat.dateFormat("2000-05-22"))
    }
}