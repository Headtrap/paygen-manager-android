package com.pineconeapps.paygenmanager.service.endpoint

import com.pineconeapps.paygenmanager.entity.Provider
import com.pineconeapps.paygenmanager.entity.dto.ImagesDTO
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ProviderEndpoint {

    @POST("provider/addProvider")
    fun addProvider(@Body provider: Provider): Observable<String>

    @POST("provider/setImages/{providerId}")
    fun setImages(@Path("providerId") providerId: String, @Body images: ImagesDTO): Observable<String>

    @POST("provider/getImages/{providerId}")
    fun getImages(@Path("providerId") providerId: String): Observable<ImagesDTO>
}