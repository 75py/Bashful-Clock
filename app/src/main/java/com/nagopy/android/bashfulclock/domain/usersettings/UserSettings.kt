package com.nagopy.android.bashfulclock.domain.usersettings

interface UserSettings {

    val inEnglish: Boolean

    val duration: Float

    val dateFormat: String

    val fadeOut: Boolean

    val textSize: String
    val textSizeSmall: String
    val textSizeMedium: String
    val textSizeLarge: String
    val textSizeExtraLarge: String

    fun isUserSettingsKey(key: String?): Boolean

    var y: Int

}
