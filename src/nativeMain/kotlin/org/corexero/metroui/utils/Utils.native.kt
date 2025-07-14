package org.corexero.metroui.utils

import platform.Foundation.NSBundle
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual val platformName: String
    get() = "iOS"

actual val appVersion: String
    get() = NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String
        ?: "Unknown"

actual fun currentTimeMillis(): Long {
    return NSDate().timeIntervalSince1970.toLong() * 1000
}