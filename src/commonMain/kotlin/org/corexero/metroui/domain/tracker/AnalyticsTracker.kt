package org.corexero.metroui.domain.tracker

import org.corexero.metroui.analytics.ScreenName

interface AnalyticsTracker {

    fun logEvent(name: String, screenName: ScreenName, params: Map<String, Any?>? = null)

    fun setGlobalProperties(params: Map<String, Any>?)

    fun setUserId(id: String)

}