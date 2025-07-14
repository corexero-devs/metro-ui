package org.corexero.metroui.data.repositoryImpl

import org.corexero.metroui.analytics.ScreenName
import org.corexero.metroui.domain.tracker.AnalyticsTracker

expect class FirebaseAnalyticsTracker : AnalyticsTracker {
    override fun setGlobalProperties(params: Map<String, Any>?)
    override fun logEvent(name: String, screenName: ScreenName, params: Map<String, Any?>?)
    override fun setUserId(id: String)
}