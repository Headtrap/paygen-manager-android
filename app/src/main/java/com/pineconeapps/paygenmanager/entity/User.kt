package com.pineconeapps.paygenmanager.entity

import java.io.Serializable


/**
 * Created by Headtrap on 28/08/2017.
 */
open class User : Serializable {

    lateinit var id: String

    lateinit var name: String

    lateinit var email: String

    lateinit var password: String

    lateinit var phone: String
}
