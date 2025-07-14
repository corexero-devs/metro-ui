package org.corexero.metroui.analytics


object FirebaseAnalyticsEvents {
    const val APP_LAUNCH = "app_launch"
    const val GET_ROUTE = "get_route"
    const val GET_RECENT_ROUTE = "get_recent_route"
    const val BOOK_TICKET = "book_ticket"
    const val METRO_MAP = "metro_map"
    const val FIRST_LAST_METRO = "first_last_metro"
    const val SOURCE_SELECT = "source_select"
    const val DEST_SELECT = "dest_select"
    const val ROUTE_LOAD_TIME = "route_load_time"
    const val REVIEW_POP_UP_SHOW = "review_pop_up_show"
    const val LAST_METRO_TIME_SOURCE_SELECT = "last_metro_time_source_select"
    const val LAST_METRO_TIME_DEST_SELECT = "last_metro_time_dest_select"
}

object FirebaseAnalyticsParams {
    const val APP_VERSION = "app_version"
    const val PLATFORM_TYPE = "platform_type"
    const val ERROR = "error"
    const val SOURCE_ID = "source_id"
    const val DEST_ID = "dest_id"
    const val SOURCE_NAME = "source_name"
    const val DEST_NAME = "dest_name"
    const val TIME = "time"
}

enum class ScreenName {
    HOME_SCREEN,
    ROUTE_SCREEN
}

