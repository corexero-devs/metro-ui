package org.corexero.metroui.data.repositoryImpl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.corexero.metroui.data.datastore.DataStoreKey
import org.corexero.metroui.data.datastore.DataStoreManager
import org.corexero.metroui.domain.model.RouteResultUi
import org.corexero.metroui.domain.repository.RecentSearchRepository

class RecentSearchRepositoryImpl(
    private val dataStoreManager: DataStoreManager
) : RecentSearchRepository {

    override suspend fun addOrReorder(
        routeResultUi: RouteResultUi
    ) {
        withContext(Dispatchers.IO) {
            val routeResults = getRecentRouteResults().toMutableList()
            routeResults.removeAll {
                it.sourceStation.id == routeResultUi.sourceStation.id
                        && it.destinationStation.id == routeResultUi.destinationStation.id
            }
            routeResults.add(0, routeResultUi)
            if (routeResults.size > RECENT_SEARCHES_LIMIT) {
                routeResults.subList(
                    RECENT_SEARCHES_LIMIT,
                    routeResults.size
                ).clear()
            }
            val routeResultJson = Json.encodeToString(routeResults)
            dataStoreManager.put(DataStoreKey.RecentSearches, routeResultJson)
        }
    }

    override suspend fun getRecentSearches(): Flow<List<RouteResultUi>> {
        return dataStoreManager.getFlow(DataStoreKey.RecentSearches)
            .map { routeResultListJson ->
                if (routeResultListJson.isNotEmpty()) {
                    runCatching {
                        Json.decodeFromString<List<RouteResultUi>>(routeResultListJson)
                    }.onSuccess { routeResults ->
                        return@map routeResults
                    }
                }
                emptyList()
            }
    }

    private suspend fun getRecentRouteResults(): List<RouteResultUi> {
        val routeResultListJson = dataStoreManager.getFirst(DataStoreKey.RecentSearches)
        return try {
            Json.decodeFromString<List<RouteResultUi>>(routeResultListJson)
        } catch (_: Exception) {
            listOf()
        }
    }

    override suspend fun getRecentSearch(
        sourceId: Int,
        destinationId: Int
    ): RouteResultUi? {
        return getRecentRouteResults().find {
            it.sourceStation.id == sourceId
                    && it.destinationStation.id == destinationId
        }
    }

    companion object {
        const val RECENT_SEARCHES_LIMIT = 3
    }

}