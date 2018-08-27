package com.pineconeapps.paygenmanager.activity

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
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

    var startLength = 0
    var endLength = 0
    private var locked = false
    private val sdf: SimpleDateFormat
        @SuppressLint("SimpleDateFormat")
        get() = SimpleDateFormat("dd/MM/yyyy")

    private val startWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                if (text != null) {
                    if (text.length > startLength) {

                        if (text.length == 2 || text.length == 5) {
                            text.append('/')
                        }
                    }
                    startLength = text.length
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }
    private val endWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                if (text != null) {
                    if (text.length > endLength) {

                        if (text.length == 2 || text.length == 5) {
                            text.append('/')
                        }
                    }
                    endLength = text.length
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)
        setupActionBar()
        recyclerView.layoutManager = LinearLayoutManager(this)
        etEndDate.addTextChangedListener(endWatcher)
        etStartDate.addTextChangedListener(startWatcher)
        btOk.setOnClickListener { filterDate() }
        lFilter.setOnClickListener { lFilter.visibility = View.GONE }
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
            R.id.action_custom_date -> filterCustom()
            else -> super.onBackPressed()
        }
        return true
    }

    private fun filterCustom() {
        locked = true
        lFilter.visibility = View.VISIBLE
    }


    private fun filterDate() {
        var startDate: String = etStartDate.text.toString()
        var endDate: String = etEndDate.text.toString()

        if (startDate.isEmpty() || endDate.isEmpty()) {
            showWarning(getString(R.string.warning_empty_fields))
            return
        }
        if (startDate.length != 10 || endDate.length != 10) {
            showWarning(getString(R.string.warning_invalid_date))
            return
        }

        try {
            val calendar = Calendar.getInstance()


            val dateS = sdf.parse(startDate)

            val dateE = sdf.parse(endDate)
            calendar.time = dateE
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val adjustedDateE = calendar.time

            startDate = sdf.format(dateS)
            endDate = sdf.format(adjustedDateE)

            if (dateS.after(dateE)) {
                showWarning(getString(R.string.warning_start_end_date))
                return
            }

        } catch (e: Exception) {
            showWarning(getString(R.string.warning_invalid_date))
            return
        }

        locked = false
        etEndDate.setText("")
        etStartDate.setText("")
        lFilter.visibility = View.GONE
        filterSales(startDate, endDate)
    }

    private fun filterSales(startDate: String, endDate: String) {
        showProgress()
        TransactionService.filterSales(prefs.providerId, DateFilter(startDate, endDate)).applySchedulers().subscribe(
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
            if (!locked) {
                startActivity<SaleDetailsActivity>("transaction" to it)
            }
        }
        recyclerView.adapter = adapter
    }

    private fun getFilter(daysAgo: Int): DateFilter {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val endDate = sdf.format(calendar.time)
        val startDate = sdf.format(getDaysAgo(daysAgo))
        return DateFilter(startDate, endDate)
    }

    private fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

        return calendar.time
    }
}