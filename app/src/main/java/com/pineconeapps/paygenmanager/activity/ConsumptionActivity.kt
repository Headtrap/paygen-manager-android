package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.adapter.ItemAdapter
import com.pineconeapps.paygenmanager.entity.Consumption
import com.pineconeapps.paygenmanager.entity.Customer
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.ConsumptionService
import kotlinx.android.synthetic.main.activity_consumption.*

class ConsumptionActivity : BaseActivity() {

    private lateinit var customer: Customer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumption)
        customer = intent.getSerializableExtra("customer") as Customer
    }

    override fun onStart() {
        super.onStart()
        initViews()
        getConsumption()
    }


    private fun initViews() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        getConsumption()
    }

    private fun getConsumption() {
        showProgress()
        ConsumptionService.getConsumption(customerId = customer.id, providerId = prefs.providerId).applySchedulers().subscribe(
                {
                    createAdapter(it)
                },
                {
                    closeProgress()
                    handleException(it)
                },
                {
                    closeProgress()
                }
        )
    }

    private fun createAdapter(consumption: Consumption) {
        val adapter = ItemAdapter(consumption.items)
        recyclerView.adapter = adapter
    }
}
