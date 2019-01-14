package com.nagopy.android.bashfulclock.infra.remoteconfig

import com.google.gson.annotations.SerializedName

data class RemoteConfigJapaneseEra(
    @SerializedName("endTime")
    val endTime: Long,
    @SerializedName("startYear")
    val startYear: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("shortName")
    val shortName: String
)
