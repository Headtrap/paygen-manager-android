package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.adapter.ItemAdapter
import com.pineconeapps.paygenmanager.entity.Transaction
import kotlinx.android.synthetic.main.activity_sale_details.*
import com.pineconeapps.paygenmanager.util.StringUtils.currency


class SaleDetailsActivity : BaseActivity() {
    private lateinit var transaction: Transaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_details)
        setupActionBar()
        recyclerView.layoutManager = LinearLayoutManager(this)
        transaction = intent.getSerializableExtra("transaction") as Transaction
    }

    override fun onStart() {
        super.onStart()
        setData()
    }

    private fun setData() {
        val adapter = ItemAdapter(transaction.items)
        recyclerView.adapter = adapter

        tvId.text = transaction.id
        tvData.text = transaction.date
        tvDiscount.text = transaction.discount.currency()
        tvTotal.text = transaction.total.currency()
    }

}
