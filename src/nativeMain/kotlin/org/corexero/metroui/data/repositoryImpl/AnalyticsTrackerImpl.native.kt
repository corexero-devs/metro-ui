package org.corexero.metroui.data.repositoryImpl

import org.corexero.metroui.analytics.ScreenName
import org.corexero.metroui.domain.tracker.AnalyticsTracker

actual object FirebaseAnalyticsTracker : AnalyticsTracker {

    private var analyticsTracker: AnalyticsTracker? = null

    fun setupAnalyticsTracer(analyticsTracker: AnalyticsTracker) {
        this.analyticsTracker = analyticsTracker
    }

    actual override fun logEvent(
        name: String,
        screenName: ScreenName,
        params: Map<String, Any?>?
    ) {
        analyticsTracker?.logEvent(name, screenName, params)
    }

    actual override fun setGlobalProperties(params: Map<String, Any>?) {
        analyticsTracker?.setGlobalProperties(params)
    }

    actual override fun setUserId(id: String) {
        analyticsTracker?.setUserId(id)
    }

}