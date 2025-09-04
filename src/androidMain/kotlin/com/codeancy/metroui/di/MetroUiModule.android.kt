package com.codeancy.metroui.di

import com.codeancy.metroui.common.utils.IntentUtils
import com.codeancy.metroui.data.repositoryImpl.AppUpdateRepositoryImpl
import com.codeancy.metroui.domain.repository.AppUpdateRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platFormMetroUiModule: Module
    get() = module {
        singleOf(::AppUpdateRepositoryImpl)
            .bind<AppUpdateRepository>()
        singleOf(::IntentUtils)
    }