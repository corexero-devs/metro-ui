package com.codeancy.metroui.domain.repository

import com.codeancy.metroui.domain.models.RecentRouteResult
import com.codeancy.metroui.domain.models.RouteResultUi
import kotlinx.coroutines.flow.Flow

interface RouteRepository {

    suspend fun getRoute(
        sourceId: Long,
        destinationId: Long
    ): RouteResultUi

    suspend fun updatePlatForms(
        routeResultUi: RouteResultUi
    ): RouteResultUi

    suspend fun getRecentSearches(): Flow<List<RecentRouteResult>>

    suspend fun getRecentSearch(sourceId: Long, destinationId: Long): RecentRouteResult?

}