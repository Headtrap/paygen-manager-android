package com.pineconeapps.paygenmanager.service.endpoint

import com.pineconeapps.paygenmanager.entity.Consumption
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface ConsumptionEndpoint {

    @GET("consumption/getConsumption/{customerId}/{providerId}")
    fun getConsumption(
            @Path("customerId") customerId: String,
            @Path("providerId") providerId: String
    ): Observable<Consumption>

}