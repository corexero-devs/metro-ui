package org.corexero.metroui.utils

import android.os.Build

actual val platformName = "android"

actual val appVersion = Build.VERSION.SDK_INT.toString() + " (" + Build.VERSION.RELEASE + ")"

actual fun currentTimeMillis(): Long = System.currentTimeMillis()