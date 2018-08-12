package com.pineconeapps.paygenmanager.entity

import android.location.Address
import java.io.Serializable

class ProviderInfo(var banner: String, var address: Address, var about: String, var type: Type, var openHours: List<OpenHours>) : Serializable {

    enum class Type {
        RESTAURANT,
        HAMBURGUER,
        PIZZA
    }
}
