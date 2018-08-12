package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.adapter.AddItemAdapter
import com.pineconeapps.paygenmanager.entity.Customer
import com.pineconeapps.paygenmanager.entity.Item
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.ConsumptionService
import com.pineconeapps.paygenmanager.service.ItemService
import kotlinx.android.synthetic.main.activity_add_items.*

class AddItemsActivity : BaseActivity() {

    private lateinit var customer: Customer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_items)
        setupActionBar()
        setupToolbar(R.string.title_add_items)
        customer = intent.getSerializableExtra("customer") as Customer

    }

    override fun onStart() {
        super.onStart()
        initView()
        listProducts()
    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        btSearch.setOnClickListener { findProducts() }
    }

    private fun listProducts() {
        showProgress()
        ItemService.listProducts(prefs.providerId).applySchedulers().subscribe(
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

    private fun findProducts() {
        showProgress()
        val query = etQuery.text.toString()
        if (query.isEmpty()) {
            showWarning(getString(R.string.error_empty_query))
            return
        }
        ItemService.findItems(prefs.providerId, query).applySchedulers().subscribe(
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

    private fun addItem(item: Item) {
        showProgress()
        ConsumptionService.addItem(prefs.providerId, customer.id, item.id).applySchedulers().subscribe(
                {
                    showMessage(it)
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

    private fun createAdapter(list: List<Item>) {
        var adapter = AddItemAdapter(list) {
            addItem(it)
        }
        recyclerView.adapter = adapter
    }

}
