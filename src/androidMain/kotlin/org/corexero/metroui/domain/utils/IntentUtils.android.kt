package org.corexero.metroui.domain.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.FileProvider
import com.google.android.play.core.review.ReviewManagerFactory
import java.io.File
import java.io.FileOutputStream

actual class IntentUtils(
    private val activity: Activity
) {

    private val reviewManager by lazy {
        ReviewManagerFactory.create(activity)
    }

    actual fun onShareMetroRoute(imageBitmap: ImageBitmap) {
        activity.startActivity(createShareImageIntent(activity, imageBitmap))
    }

    private fun createShareImageIntent(context: Context, imageBitmap: ImageBitmap): Intent? {
        val bitmap = imageBitmap.asAndroidBitmap()
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "shared_image.png")
        FileOutputStream(file).use { stream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        }

        val contentUri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        return Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, contentUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    actual fun showInAppReview() {
        val request = reviewManager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = reviewManager.launchReviewFlow(activity, reviewInfo)
                flow.addOnCompleteListener { result ->
                    Log.d("IntentUtils", "In-app review completed: $result")
                }
            } else {
                Log.e("IntentUtils", "Failed to request in-app review: ${task.exception?.message}")
            }
        }
    }

}