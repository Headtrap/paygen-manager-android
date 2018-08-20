package com.pineconeapps.paygenmanager.service.endpoint

import com.pineconeapps.paygenmanager.entity.dto.LoginDTO
import com.pineconeapps.paygenmanager.entity.Provider
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface AccessEndpoint {
    @GET("access/validateProvider/{email}/{password}")
    fun validateProvider(@Path("email") email: String,
                         @Path("password") password: String): Observable<LoginDTO>

    @GET("access/validateEmployee/{email}/{password}")
    fun validateEmployee(@Path("email") email: String,
                         @Path("password") password: String): Observable<LoginDTO>

    @GET("access/check/{email}")
    fun checkProvider(@Path("email") email: String): Observable<Provider>
}