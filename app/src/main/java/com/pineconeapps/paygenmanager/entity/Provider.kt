package com.pineconeapps.paygenmanager.entity

import com.example.gustavobatista.paygen.entity.Lobby
import com.pineconeapps.paygenmanager.PaygenApplication
import com.pineconeapps.paygenmanager.R


/**
 * Created by Headtrap on 28/08/2017.
 */

class Provider(var lobby: Lobby,
               var location: Point,
               var sales: List<Transaction>,
               var consumptions: List<Consumption>,
               var employees: List<User>,
               var info: ProviderInfo) : User("", "", "", "", "") {


    enum class Status {
        PENDING,
        ACTIVE
    }

}
