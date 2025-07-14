package org.corexero.metroui.domain.utils

object Logger {
    private const val DEFAULT_TAG = "APP_LOG"

    fun log(tag: String = DEFAULT_TAG, message: String) {
        printLog(tag, message)
    }

    fun error(tag: String = DEFAULT_TAG, message: String, throwable: Throwable? = null) {
        printLog(tag, "❌ ERROR: $message", throwable)
    }

    private fun printLog(tag: String, message: String, throwable: Throwable? = null) {
        val formattedMessage = "[$tag] $message"
        when {

        }
    }
}