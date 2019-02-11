package com.nagopy.android.bashfulclock.data.remoteconfig

import kotlinx.serialization.Serializable

@Serializable
data class RemoteConfigJapaneseEras(
    val eras: List<RemoteConfigJapaneseEra>
)

@Serializable
data class RemoteConfigJapaneseEra(
    val endTime: Long,
    val startYear: Int,
    val name: String,
    val shortName: String
)
