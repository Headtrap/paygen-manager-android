package com.example.gustavobatista.paygen.entity

import com.pineconeapps.paygenmanager.entity.Customer
import java.io.Serializable

class Lobby : Serializable{

    lateinit var customerList: List<Customer>
}
