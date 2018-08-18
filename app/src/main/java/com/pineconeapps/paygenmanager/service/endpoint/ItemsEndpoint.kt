package com.pineconeapps.paygenmanager.service.endpoint

import com.pineconeapps.paygenmanager.entity.Item
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ItemsEndpoint {

    @GET("product/listProducts/{providerId}")
    fun listProducts(@Path("providerId") providerId: String): Observable<List<Item>>

    @GET("item/findProduct/{providerId}/{query}")
    fun findProduct(@Path("providerId") providerId: String,
                    @Path("query") query: String): Observable<List<Item>>

}