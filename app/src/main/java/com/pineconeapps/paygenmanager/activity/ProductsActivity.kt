package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.adapter.ProductAdapter
import com.pineconeapps.paygenmanager.entity.Product
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.ProductService
import kotlinx.android.synthetic.main.activity_products.*
import org.jetbrains.anko.startActivity

class ProductsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        setupActionBar()
    }

    override fun onStart() {
        super.onStart()
        initViews()
        getProducts()
    }

    private fun initViews() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        fabAddProduct.setOnClickListener { startActivity<AddProductActivity>("product" to null) }
    }

    private fun getProducts() {
        showProgress()
        ProductService.listProducts(prefs.providerId).applySchedulers().subscribe(
                {
                    handleList(it)
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

    private fun handleList(products: List<Product>) {
        val adapter = ProductAdapter(products) {
            startActivity<AddProductActivity>("product" to it)
        }
        recyclerView.adapter = adapter
    }
}
