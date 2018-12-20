package com.didik.footballmatchschedule.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
        val strPlayer: String?,
        val strCutout: String?,
        val strThumb: String?,
        val strPosition: String?,
        val strHeight: String?,
        val strWeight: String?,
        val strDescriptionEN: String?,
        val strNationality: String?) : Parcelable