package com.codeancy.metroui.inAppReview

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.android.play.core.review.ReviewManagerFactory


@Composable
actual fun rememberShowInAppReview(): ShowInAppReview {

    val activity = LocalActivity.current

    val manager = remember {
        activity?.applicationContext?.let {
            ReviewManagerFactory.create(it)
        }
    }

    val showInAppReview = remember(manager) {
        object : ShowInAppReview {
            override fun show() {
                val request = manager?.requestReviewFlow()
                request?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // We got the ReviewInfo object
                        val reviewInfo = task.result
                        val flow = activity?.let { activity ->
                            manager.launchReviewFlow(activity, reviewInfo)
                        }
                        flow?.addOnCompleteListener { _ ->
                            // The flow has finished. The API does not indicate whether the user
                            // reviewed or not, or even whether the review dialog was shown. Thus, no
                            // matter the result, we continue our app flow.
                        }
                    } else {
                        // There was some problem, log or handle the error code.
                    }
                }
            }
        }
    }

    return showInAppReview

}