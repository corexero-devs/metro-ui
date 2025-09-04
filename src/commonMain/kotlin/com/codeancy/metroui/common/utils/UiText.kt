package com.codeancy.metroui.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Stable
sealed interface UiText {

    @Stable
    data class AppStringResource(
        val resource: StringResource,
        val args: Array<Any> = arrayOf()
    ) : UiText {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as AppStringResource

            if (resource != other.resource) return false
            if (!args.contentEquals(other.args)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = resource.hashCode()
            result = 31 * result + args.contentHashCode()
            return result
        }
    }

    @Stable
    data class DynamicString(
        val value: String
    ) : UiText

    @Composable
    @Stable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is AppStringResource -> stringResource(resource, *args)
        }
    }
}