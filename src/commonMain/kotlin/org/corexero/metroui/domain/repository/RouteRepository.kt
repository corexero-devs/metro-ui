package org.corexero.metroui.domain.repository

import org.corexero.metroui.domain.model.RouteResultUi

interface RouteRepository {

    suspend fun getRoute(
        sourceId: String,
        destinationId: String
    ): RouteResultUi

    suspend fun updatePlatForms(
        routeResultUi: RouteResultUi
    ): RouteResultUi

}