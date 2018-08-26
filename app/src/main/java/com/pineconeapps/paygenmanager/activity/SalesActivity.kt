package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.adapter.TransactionAdapter
import com.pineconeapps.paygenmanager.entity.Transaction
import com.pineconeapps.paygenmanager.entity.dto.DateFilter
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.TransactionService
import kotlinx.android.synthetic.main.activity_sales.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*


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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_one_day -> filterSales(1)
            R.id.action_seven_days -> filterSales(7)
            R.id.action_thirty_days -> filterSales(30)
        }
        return true
    }


    private fun filterSales(daysAgo: Int) {

        showProgress()
        TransactionService.filterSales(prefs.providerId, getFilter(daysAgo)).applySchedulers().subscribe(
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

    private fun getFilter(daysAgo: Int): DateFilter {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val endDate = sdf.format(Date())
        val startDate = sdf.format(getDaysAgo(daysAgo))
        return DateFilter(startDate, endDate)
    }

    private fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

        return calendar.time
    }
}
