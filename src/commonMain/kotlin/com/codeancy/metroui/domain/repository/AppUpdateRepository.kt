package com.codeancy.metroui.domain.repository

import com.codeancy.metroui.domain.models.AppUpdateUi

interface AppUpdateRepository {

    suspend fun getAppUpdateInfo(): AppUpdateUi?

}