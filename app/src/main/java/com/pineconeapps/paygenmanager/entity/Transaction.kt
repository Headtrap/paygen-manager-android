package com.pineconeapps.paygenmanager.entity

import java.io.Serializable
import java.util.*

open class Transaction(var items: List<Item>,
                       var total: Double,
                       var customerId: String,
                       var providerId: String,
                       var id: String? = null) : Serializable {

    var payment: Payment? = null

    var discount: Double = 0.0

    var date: String = ""

    var type: Transaction.Type = Type.LOCAL

    enum class Type {
        DELIVERY,
        LOCAL
    }

    enum class Status {
        PAID,
        PENDING,
        CANCELED
    }


}
