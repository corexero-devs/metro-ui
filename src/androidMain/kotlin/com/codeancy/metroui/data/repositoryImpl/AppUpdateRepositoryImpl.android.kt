package com.codeancy.metroui.data.repositoryImpl


import android.content.Context
import android.os.Build
import com.codeancy.metroui.firebase.MetroConfigKey
import com.codeancy.metroui.domain.models.AppUpdateUi
import com.codeancy.metroui.domain.repository.AppUpdateRepository
import com.codeancy.metroui.data.dtos.AppUpdateInfoDto
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.Json
import org.corexero.sutradhar.remoteConfig.FirebaseRemoteConfig
import org.corexero.sutradhar.utils.Platform
import org.corexero.sutradhar.utils.platform

actual class AppUpdateRepositoryImpl(
    private val context: Context
) : AppUpdateRepository {

    actual override suspend fun getAppUpdateInfo(): AppUpdateUi? {
        val appUpdateInfo = FirebaseRemoteConfig.getString(MetroConfigKey.AppUpdateInfo)
        return try {
            val appUpdateInfoDto =
                Json.Default.decodeFromString<AppUpdateInfoDto?>(appUpdateInfo) ?: return null
            if (appUpdateInfoDto.newVersionCode > getCurrentVersionCode()) {
                AppUpdateUi(
                    isHardUpdate = appUpdateInfoDto.isHardUpdate,
                    appUrl = when (platform) {
                        Platform.Ios -> appUpdateInfoDto.iosAppUrl
                        Platform.Android -> appUpdateInfoDto.androidAppUrl
                    },
                    newVersion = appUpdateInfoDto.newVersionCode
                )
            } else {
                null
            }
        } catch (err: Exception) {
            if (err is CancellationException) throw err
            return null
        }
    }

    private fun getCurrentVersionCode(): Int {
        val packageInfo = context.packageManager
            .getPackageInfo(context.packageName, 0)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode.toInt()
        } else {
            @Suppress("DEPRECATION")
            packageInfo.versionCode
        }
    }

}