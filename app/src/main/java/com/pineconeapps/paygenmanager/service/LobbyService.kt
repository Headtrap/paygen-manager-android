package com.pineconeapps.paygenmanager.service

import com.pineconeapps.paygenmanager.service.endpoint.LobbyEndpoint

object LobbyService : Service() {
    private val service: LobbyEndpoint
        get() = createService(LobbyEndpoint::class.java)

    fun getCustomers(providerId: String) = service.getCustomers(providerId)
}