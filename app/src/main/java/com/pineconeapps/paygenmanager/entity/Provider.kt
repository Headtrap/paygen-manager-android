package com.pineconeapps.paygenmanager.entity

import com.example.gustavobatista.paygen.entity.Lobby
import com.pineconeapps.paygenmanager.PaygenApplication
import com.pineconeapps.paygenmanager.R


/**
 * Created by Headtrap on 28/08/2017.
 */

class Provider(var status: Status,
               var lobby: Lobby,
               var location: Point,
               var sales: List<Transaction>,
               var consumptions: List<Consumption>,
               var employees: List<User>,
               var info: ProviderInfo) : User("", "", "", "", "") {


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
