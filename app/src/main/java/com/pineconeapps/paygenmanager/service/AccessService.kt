package com.pineconeapps.paygenmanager.service

import com.pineconeapps.paygenmanager.service.endpoint.AccessEndpoint

object AccessService : Service() {
    private val service: AccessEndpoint
        get() = createService(AccessEndpoint::class.java)

    fun validateProvider(email: String, password: String) = service.validateProvider(email, password)

    fun validateEmployee(email: String, password: String) = service.validateEmployee(email, password)

    fun checkProvider(email: String) = service.checkProvider(email)
}