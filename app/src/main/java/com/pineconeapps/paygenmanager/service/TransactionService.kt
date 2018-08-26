package com.pineconeapps.paygenmanager.service

import com.pineconeapps.paygenmanager.entity.dto.DateFilter
import com.pineconeapps.paygenmanager.service.endpoint.TransactionEndpoint

object TransactionService : Service() {
    val service: TransactionEndpoint
        get() = createService(TransactionEndpoint::class.java)

    fun getTransactions(providerId: String) = service.getTransactions(providerId)

    fun filterSales(providerId: String, filter: DateFilter) = service.filterSales(providerId, filter)
}