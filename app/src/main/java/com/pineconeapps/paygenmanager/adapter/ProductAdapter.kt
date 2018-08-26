package com.pineconeapps.paygenmanager.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.entity.Product
import com.pineconeapps.paygenmanager.util.StringUtils.currency
import kotlinx.android.synthetic.main.adapter_product.view.*

class ProductAdapter(private val products: List<Product>, val listener: (Product) -> Unit) :
        RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_product,
                parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(products[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(product: Product, listener: (Product) -> Unit) = with(itemView) {
            tvPrice.text = product.price.currency()
            tvDesc.text = product.name
            tvStock.text = product.amount.toString()
            setOnClickListener { listener(product) }
        }
    }

    override fun getItemCount(): Int = products.size
}
