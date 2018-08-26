package com.pineconeapps.paygenmanager.service.endpoint

import com.pineconeapps.paygenmanager.entity.Transaction
import com.pineconeapps.paygenmanager.entity.dto.DateFilter
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionEndpoint {

    @GET("transaction/getTransactions/{providerId}")
    fun getTransactions(@Path("providerId") providerId: String): Observable<List<Transaction>>

    @POST("transaction/filterSales/{providerId}")
    fun filterSales(@Path("providerId") providerId: String,
                           @Body filter: DateFilter): Observable<List<Transaction>>
}