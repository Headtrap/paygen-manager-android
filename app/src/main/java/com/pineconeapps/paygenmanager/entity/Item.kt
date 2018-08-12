package com.pineconeapps.paygenmanager.entity

import java.io.Serializable


open class Item(var id: String,
                var description: String,
                var value: Double,
                var price: Double,
                var discount: Double) : Serializable {
    constructor() : this("", "", 0.0, 0.0, 0.0)
}