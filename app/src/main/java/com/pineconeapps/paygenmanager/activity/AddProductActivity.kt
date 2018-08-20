package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.entity.Product
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.ProductService
import com.pineconeapps.paygenmanager.util.StringUtils.currency
import kotlinx.android.synthetic.main.activity_add_product.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class AddProductActivity : BaseActivity() {

    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        setupActionBar()

        btSave.setOnClickListener { saveProduct() }

        product = intent.getSerializableExtra("product") as Product?
        if (product != null) {
            populate()
        } else {
            product = Product(0)
        }
    }

    private fun populate() {
        etAmount.setText(product?.amount.toString())
        etDesc.setText(product?.description)
        etDiscount.setText(product?.discount?.currency())
        etValue.setText(product?.value?.currency())
    }

    private fun saveProduct() {
        if (etValue.text.isNullOrEmpty() || etDesc.text.isNullOrEmpty() ||
                etDiscount.text.isNullOrEmpty() || etAmount.text.isNullOrEmpty()) {
            showWarning(R.string.warning_empty_fields)
            return
        }
        product?.amount = (etAmount.text).toString().toLong()
        product?.description = etDesc.text.toString()
        product?.value = etValue.getValue()
        product?.discount = etDiscount.getValue()
        product?.price = product!!.value - product!!.discount

        showProgress()
        ProductService.addProduct(product, prefs.providerId).applySchedulers().subscribe(
                {
                    handleResult(it)
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

    private fun handleResult(message: String) {
        alert(message, getString(R.string.title_success))
        {
            yesButton {
                finish()
            }
        }.show()
    }
}
