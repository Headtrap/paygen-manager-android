package com.pineconeapps.paygenmanager.entity.dto

import com.pineconeapps.paygenmanager.entity.User

class LoginDTO {
    val providerId: String = ""
    val userId: String = ""
    val token: String = ""
    val userName: String = ""
    val picture: String = ""

    var status: User.Status? = null

}