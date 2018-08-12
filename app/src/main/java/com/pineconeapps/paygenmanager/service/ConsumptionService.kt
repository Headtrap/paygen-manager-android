package com.pineconeapps.paygenmanager.service

import com.pineconeapps.paygenmanager.entity.Consumption
import com.pineconeapps.paygenmanager.service.endpoint.ConsumptionEndpoint
import io.reactivex.Observable

object ConsumptionService {
    private val service: ConsumptionEndpoint
        get() = Service.createService(ConsumptionEndpoint::class.java)

    fun getConsumption(customerId: String, providerId: String): Observable<Consumption> =
            service.getConsumption(customerId, providerId)
}
