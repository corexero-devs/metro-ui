package com.codeancy.metroui.data.repositoryImpl

import com.codeancy.metroui.domain.models.AppUpdateUi
import com.codeancy.metroui.domain.repository.AppUpdateRepository

expect class AppUpdateRepositoryImpl  : AppUpdateRepository {
    override suspend fun getAppUpdateInfo(): AppUpdateUi?
}