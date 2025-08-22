package org.corexero.metroui.domain.utils

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.CoreGraphics.CGBitmapContextCreate
import platform.CoreGraphics.CGBitmapContextCreateImage
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.CoreGraphics.CGImageAlphaInfo
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIImage
import platform.UIKit.UIViewController
import platform.UIKit.popoverPresentationController

actual class IntentUtils(
    private val uiViewController: UIViewController
) {

    actual fun onShareMetroRoute(imageBitmap: ImageBitmap) {
        convertImageBitmapToUIImage(imageBitmap)?.let { uiImage ->
            val activityVC = UIActivityViewController(
                activityItems = listOf(uiImage),
                applicationActivities = null
            )
            activityVC.popoverPresentationController?.sourceView = uiViewController.view
            uiViewController.presentViewController(
                activityVC,
                true,
                null
            )
        }

    }

    @OptIn(ExperimentalForeignApi::class)
    private fun convertImageBitmapToUIImage(imageBitmap: ImageBitmap): UIImage? {
        val width = imageBitmap.width
        val height = imageBitmap.height
        val buffer = IntArray(width * height)

        imageBitmap.readPixels(buffer)

        val colorSpace = CGColorSpaceCreateDeviceRGB()
        val context = CGBitmapContextCreate(
            data = buffer.refTo(0),
            width = width.toULong(),
            height = height.toULong(),
            bitsPerComponent = 8u,
            bytesPerRow = (4 * width).toULong(),
            space = colorSpace,
            bitmapInfo = CGImageAlphaInfo.kCGImageAlphaPremultipliedLast.value
        )

        val cgImage = CGBitmapContextCreateImage(context)
        return cgImage?.let { UIImage.imageWithCGImage(it) }
    }

    actual fun showInAppReview() {
        // In iOS, in-app review is handled by StoreKit, which is not available in Kotlin/Native.
        // You would typically use a native iOS implementation for this.
        // Placeholder for in-app review logic.
    }

}