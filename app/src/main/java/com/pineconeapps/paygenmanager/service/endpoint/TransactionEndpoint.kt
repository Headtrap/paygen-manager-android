package com.pineconeapps.paygenmanager.service.endpoint

import com.pineconeapps.paygenmanager.entity.Transaction
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionEndpoint {

    @POST("transaction/getTransactions/{providerId}")
    fun getTransactions(@Path("providerId") providerId: String): Observable<List<Transaction>>
}