package org.corexero.metroui.domain.utils

import kotlinx.coroutines.CancellationException

inline fun <T> runCatchingOrNull(block: () -> T): T? {
    return try {
        block()
    } catch (err: Exception) {
        if (err is CancellationException) throw err
        null
    }
}