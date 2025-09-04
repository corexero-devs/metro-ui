package com.codeancy.metroui.common.utils

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

actual class IntentUtils(
    private val uiViewController: UIViewController
) {

    actual fun onShareMetroRoute(imageBitmap: ImageBitmap) {
        convertImageBitmapToUIImage(imageBitmap)?.let { uiImage ->
            val activityVC = UIActivityViewController(
                activityItems = listOf(uiImage),
                applicationActivities = null
            )
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
        return cgImage?.let { UIImage.Companion.imageWithCGImage(it) }
    }


}