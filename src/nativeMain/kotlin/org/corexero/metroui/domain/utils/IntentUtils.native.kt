package org.corexero.metroui.domain.utils

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGBitmapContextCreate
import platform.CoreGraphics.CGBitmapContextCreateImage
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.CoreGraphics.CGImageAlphaInfo
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSThread
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIImage
import platform.UIKit.UIPopoverArrowDirection
import platform.UIKit.UIPopoverArrowDirectionAny
import platform.UIKit.UIViewController
import platform.UIKit.popoverPresentationController
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

actual class IntentUtils(
    private val uiViewController: UIViewController
) {

    @OptIn(ExperimentalForeignApi::class)
    actual fun onShareMetroRoute(imageBitmap: ImageBitmap) {

        val presenter = uiViewController.presentedViewController ?: uiViewController

        convertImageBitmapToUIImage(imageBitmap)?.let { uiImage ->
            val activityVC = UIActivityViewController(
                activityItems = listOf(uiImage),
                applicationActivities = null
            )
            activityVC.popoverPresentationController?.let { pop ->
                pop.sourceView = presenter.view
                val bounds = presenter.view.bounds
                val w: Double = bounds.useContents { size.width }
                val h: Double = bounds.useContents { size.height }
                pop.sourceRect = CGRectMake(w / 2.0, h / 2.0, 1.0, 1.0)
                pop.permittedArrowDirections = UIPopoverArrowDirectionAny
            }
            val presentBlock = {
                presenter.presentViewController(activityVC, true, null)
            }
            if (NSThread.isMainThread()) presentBlock() else dispatch_async(
                dispatch_get_main_queue(),
                presentBlock
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