package com.pineconeapps.paygenmanager.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.entity.Item
import com.pineconeapps.paygenmanager.util.StringUtils.currency
import kotlinx.android.synthetic.main.adapter_man_item.view.*

class ItemManAdapter(private val items: List<Item>, var listener: (Item) -> Unit) :
        RecyclerView.Adapter<ItemManAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_man_item,
                parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(item: Item, listener: (Item) -> Unit) = with(itemView) {
            tvPrice.text = item.price.currency()
            tvDesc.text = item.name
            btDelete.setOnClickListener { listener(item) }
        }
    }

    override fun getItemCount(): Int = items.size
}
