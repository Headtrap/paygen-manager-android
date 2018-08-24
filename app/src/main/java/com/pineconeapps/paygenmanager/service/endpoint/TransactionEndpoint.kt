package com.pineconeapps.paygenmanager.service.endpoint

import com.pineconeapps.paygenmanager.entity.Transaction
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TransactionEndpoint {

    @GET("transaction/getTransactions/{providerId}")
    fun getTransactions(@Path("providerId") providerId: String): Observable<List<Transaction>>
}