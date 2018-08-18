package com.pineconeapps.paygenmanager.service.endpoint

import com.pineconeapps.paygenmanager.entity.Employee
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EmployeeEndpoint {

    @GET("provider/listEmployees/{providerId}")
    fun listEmployees(@Path("providerId") providerId: String): Observable<List<Employee>>

    @POST("employee/add")
    fun addEmployee(@Body employee: Employee?): Observable<String>

}