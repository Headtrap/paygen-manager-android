package com.pineconeapps.paygenmanager.service

import com.pineconeapps.paygenmanager.entity.Product
import com.pineconeapps.paygenmanager.service.endpoint.ProductEndpoint

object ProductService : Service() {

    val service: ProductEndpoint
        get() = createService(ProductEndpoint::class.java)

    fun addProduct(product: Product?, providerId: String) = service.addProduct(product, providerId)

    fun listProducts(providerId: String) = service.listProducts(providerId)
}