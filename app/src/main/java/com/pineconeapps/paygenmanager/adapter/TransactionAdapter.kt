package com.pineconeapps.paygenmanager.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.util.StringUtils.currency
import com.pineconeapps.paygenmanager.entity.Transaction
import kotlinx.android.synthetic.main.adapter_transaction.view.*

class TransactionAdapter(private val transactions: List<Transaction>, val listener: (Transaction) -> Unit) :
        RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_transaction,
                parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = transactions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(transactions[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(transaction: Transaction, listener: (Transaction) -> Unit) = with(itemView) {
            tvId.text = transaction.id
            tvData.text = transaction.date
            tvDiscount.text = transaction.discount.currency()
            tvTotal.text = transaction.total.currency()
            setOnClickListener { listener(transaction) }
        }
    }

}