package com.pineconeapps.paygenmanager.service

import com.pineconeapps.paygenmanager.entity.Employee
import com.pineconeapps.paygenmanager.service.endpoint.EmployeeEndpoint

object EmployeeService : Service() {
    val service: EmployeeEndpoint
        get() = createService(EmployeeEndpoint::class.java)

    fun addEmployee(employee: Employee?) = service.addEmployee(employee)

    fun listEmployee(providerId: String) = service.listEmployees(providerId)

}