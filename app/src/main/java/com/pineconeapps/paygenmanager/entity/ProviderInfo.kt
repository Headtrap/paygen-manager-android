package com.pineconeapps.paygenmanager.entity

import com.pineconeapps.paygenmanager.PaygenApplication
import com.pineconeapps.paygenmanager.R
import java.io.Serializable

class ProviderInfo(var banner: String, var address: String, var about: String, var type: Type, var openHours: List<OpenHours>) : Serializable {


    enum class Type(var stringRes: Int) {
        RESTAURANT(R.string.restaurant),
        HAMBURGUER(R.string.hamburguer),
        PIZZA(R.string.pizza);

        override fun toString(): String {
            return PaygenApplication.applicationContext().getString(stringRes)
        }
    }
}

