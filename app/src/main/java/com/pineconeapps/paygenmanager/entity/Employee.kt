package com.pineconeapps.paygenmanager.entity

import com.pineconeapps.paygenmanager.PaygenApplication
import com.pineconeapps.paygenmanager.R

class Employee(var role: Role, var providerId: String) : User("", "", "", "", "") {

    enum class Role(var stringRes: Int) {
        MANAGER(R.string.role_manager),
        CASHIER(R.string.role_cashier),
        WAITER(R.string.role_waiter),
        ATENDANT(R.string.role_atendant);

        override fun toString(): String {
            return PaygenApplication.applicationContext().getString(stringRes)
        }
    }
}