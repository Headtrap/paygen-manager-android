package com.pineconeapps.paygenmanager.service

import com.pineconeapps.paygenmanager.entity.Provider
import com.pineconeapps.paygenmanager.service.endpoint.ProviderEndpoint

object ProviderService : Service() {
    val service: ProviderEndpoint
        get() = createService(ProviderEndpoint::class.java)

    fun addProvider(provider: Provider) = service.addProvider(provider)


}