package com.pineconeapps.paygenmanager.entity

import com.example.gustavobatista.paygen.entity.Lobby
import com.pineconeapps.paygenmanager.PaygenApplication
import com.pineconeapps.paygenmanager.R


/**
 * Created by Headtrap on 28/08/2017.
 */

class Provider(val type: Type,
               val status: Status,
               val lobby: Lobby,
               val location: Point,
               val sales: List<Transaction>,
               val consumptions: List<Consumption>,
               val employees: List<User>,
               val info: ProviderInfo) : User("", "", "", "", "") {


    enum class Status {
        PENDING,
        ACTIVE
    }

    enum class Type(var stringRes: Int) {
        RESTAURANT(R.string.restaurant),
        HAMBURGUER(R.string.hamburguer),
        PIZZA(R.string.pizza);

        override fun toString(): String {
            return PaygenApplication.applicationContext().getString(stringRes)
        }
    }

}
