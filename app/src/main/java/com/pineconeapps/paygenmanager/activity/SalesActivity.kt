package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.adapter.TransactionAdapter
import com.pineconeapps.paygenmanager.entity.Transaction
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.TransactionService
import kotlinx.android.synthetic.main.activity_sales.*
import org.jetbrains.anko.startActivity

class SalesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)
        setupActionBar()
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        getSales()
    }

    private fun getSales() {
        showProgress()
        TransactionService.getTransactions(prefs.providerId).applySchedulers().subscribe(
                {
                    setupAdapter(it)
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

    private fun setupAdapter(transactions: List<Transaction>) {
        val adapter = TransactionAdapter(transactions) {
            startActivity<SaleDetailsActivity>("transaction" to it)
        }
        recyclerView.adapter = adapter
    }
}
