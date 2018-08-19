package com.pineconeapps.paygenmanager.entity

import java.io.Serializable


/**
 * Created by Headtrap on 28/08/2017.
 */
open class User(var id: String, var name: String, var email: String, var password: String, var token: String) : Serializable {

    var status: Status? = null

    enum class Status {
        ACTIVE,
        PENDING
    }

}