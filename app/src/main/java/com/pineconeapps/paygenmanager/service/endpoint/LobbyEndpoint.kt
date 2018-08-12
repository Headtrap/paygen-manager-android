package com.pineconeapps.paygenmanager.service.endpoint

import com.example.gustavobatista.paygen.entity.Lobby
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface LobbyEndpoint {

    @GET("lobby/getCustomer/{providerId}")
    fun getCustomers(@Path("providerId") customerId: String): Observable<Lobby>

}