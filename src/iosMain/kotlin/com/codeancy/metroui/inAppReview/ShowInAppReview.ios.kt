package com.codeancy.metroui.inAppReview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.StoreKit.SKStoreReviewController
import platform.UIKit.UIApplication
import platform.UIKit.UISceneActivationStateForegroundActive
import platform.UIKit.UIWindowScene

@Composable
actual fun rememberShowInAppReview(): ShowInAppReview {

    val showInAppReview = remember {
        object : ShowInAppReview {
            override fun show() {
                val scenes = UIApplication.sharedApplication.connectedScenes
                    .filterIsInstance<UIWindowScene>()
                val activeScene =
                    scenes.firstOrNull { it.activationState == UISceneActivationStateForegroundActive }
                if (activeScene != null) {
                    SKStoreReviewController.requestReviewInScene(activeScene)
                }
            }
        }
    }

    return showInAppReview
}