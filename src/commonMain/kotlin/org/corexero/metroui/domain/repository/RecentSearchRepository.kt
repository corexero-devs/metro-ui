package org.corexero.metroui.domain.repository

import kotlinx.coroutines.flow.Flow
import org.corexero.metroui.domain.model.RouteResultUi

interface RecentSearchRepository {

    suspend fun addOrReorder(routeResultUi: RouteResultUi)

    suspend fun getRecentSearches(): Flow<List<RouteResultUi>>

    suspend fun getRecentSearch(sourceId: Int, destinationId: Int): RouteResultUi?

}