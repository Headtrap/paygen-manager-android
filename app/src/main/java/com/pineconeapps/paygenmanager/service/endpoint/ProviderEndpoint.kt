package com.pineconeapps.paygenmanager.service.endpoint

import com.pineconeapps.paygenmanager.entity.Employee
import com.pineconeapps.paygenmanager.entity.Provider
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProviderEndpoint {

    @POST("provider/addProvider")
    fun addProvider(@Body provider: Provider): Observable<String>

    @GET("employee")
    fun listEmployees(providerId: String): Observable<List<Employee>>

}