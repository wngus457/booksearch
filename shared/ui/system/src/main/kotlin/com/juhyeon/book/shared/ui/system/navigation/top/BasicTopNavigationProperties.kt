package com.juhyeon.book.shared.ui.system.navigation.top

import androidx.annotation.DrawableRes

sealed interface BasicTopNavigationProperties

sealed interface BasicTopNavigationTitle : BasicTopNavigationProperties {
    data object Off : BasicTopNavigationTitle
    data class On(val title: String, val align: TopNavigationTitleAlign = TopNavigationTitleAlign.Start) : BasicTopNavigationTitle
}

enum class TopNavigationTitleAlign {
    Start,
    Center
}

sealed interface BasicTopNavigationBackType : BasicTopNavigationProperties {
    data object Off : BasicTopNavigationBackType
    data object On : BasicTopNavigationBackType
}

sealed interface BasicTopNavigationIconType : BasicTopNavigationProperties {
    data object None : BasicTopNavigationIconType
    data object Close : BasicTopNavigationIconType
    data class Icons(val icons: List<BasicTopNavigationIcon>) : BasicTopNavigationIconType
}

data class BasicTopNavigationIcon(
    @DrawableRes val resId: Int,
    val iconId: String = ""
)