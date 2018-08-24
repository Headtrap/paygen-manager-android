package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.adapter.ItemAdapter
import com.pineconeapps.paygenmanager.entity.Consumption
import com.pineconeapps.paygenmanager.entity.Customer
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.ConsumptionService
import kotlinx.android.synthetic.main.activity_consumption.*
import org.jetbrains.anko.startActivity

class ConsumptionActivity : BaseActivity() {

    private lateinit var customer: Customer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumption)
        setupActionBar()
        setupToolbar(R.string.title_consumption)
        customer = intent.getSerializableExtra("customer") as Customer
        tvCustomerName.text = customer.name
    }

    override fun onStart() {
        super.onStart()
        initViews()
        getConsumption()
    }


    private fun initViews() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        swipeRefresh.setOnRefreshListener { getConsumption() }
        fabAddItem.setOnClickListener { startActivity<AddItemsActivity>("customer" to customer) }
        getConsumption()
    }

    private fun getConsumption() {
        swipeRefresh.isRefreshing = false
        showProgress()
        ConsumptionService.getConsumption(customer.id, prefs.providerId).applySchedulers().subscribe(
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
        if (consumption.items.isEmpty()) {
            tvEmptyRecycler.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvEmptyRecycler.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        val adapter = ItemAdapter(consumption.items)
        recyclerView.adapter = adapter
    }
}
