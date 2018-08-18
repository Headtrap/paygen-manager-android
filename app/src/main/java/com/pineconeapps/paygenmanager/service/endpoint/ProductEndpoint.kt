package com.pineconeapps.paygenmanager.service.endpoint

import com.pineconeapps.paygenmanager.entity.Product
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductEndpoint {

    @GET("product/listProducts/{providerId}")
    fun listProducts(@Path("providerId") providerId: String): Observable<List<Product>>

    @POST("product/addProduct/{providerId}")
    fun addProduct(@Body product: Product?, @Path("providerId") providerId: String): Observable<String>
}