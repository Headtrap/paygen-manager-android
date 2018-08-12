package com.pineconeapps.paygenmanager.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.entity.Customer
import kotlinx.android.synthetic.main.adapter_customer.view.*

class CustomerAdapter(private val customers: List<Customer>, private val listener: (Customer) -> Unit) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_customer,
                parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = customers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(customers[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(customer: Customer, listener: (Customer) -> Unit) = with(itemView) {
            tvEmail.text = customer.email
            tvName.text = customer.name
            setOnClickListener { listener(customer) }
        }
    }
}