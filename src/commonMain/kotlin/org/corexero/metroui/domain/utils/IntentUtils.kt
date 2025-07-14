package org.corexero.metroui.domain.utils

import androidx.compose.ui.graphics.ImageBitmap

expect class IntentUtils {
    fun onShareMetroRoute(imageBitmap: ImageBitmap)
    fun showInAppReview()
}