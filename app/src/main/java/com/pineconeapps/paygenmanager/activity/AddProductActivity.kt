package com.pineconeapps.paygenmanager.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.entity.CloudinaryResponse
import com.pineconeapps.paygenmanager.entity.Product
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.ImageService
import com.pineconeapps.paygenmanager.service.ProductService
import com.pineconeapps.paygenmanager.util.ImageUtil
import com.pineconeapps.paygenmanager.util.ImageUtil.load
import com.pineconeapps.paygenmanager.util.PermissionUtils
import com.pineconeapps.paygenmanager.util.StringUtils.currency
import kotlinx.android.synthetic.main.activity_add_product.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

class AddProductActivity : BaseActivity() {

    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        setupActionBar()

        btSave.setOnClickListener { saveProduct() }

        btTakePhoto.setOnClickListener { takePicture() }
        product = intent.getSerializableExtra("product") as Product?
        if (product != null) {
            populate()
        } else {
            product = Product(0)
        }
    }

    override fun onStart() {
        super.onStart()
        if (!PermissionUtils.checkCameraStoragePermission(getActivity())) {
            PermissionUtils.requestCameraStoragePermission(getActivity())
        }
    }

    private fun takePicture() {
        EasyImage.openChooserWithGallery(this, "Fonte da imagem", 0)
    }

    private fun populate() {
        etAmount.setText(product?.amount.toString())
        etDesc.setText(product?.description)
        etDiscount.setText(product?.discount?.currency())
        etValue.setText(product?.value?.currency())
        etName.setText(product?.name)
        imPicture.load(product?.picture) { request -> request.fit() }
    }

    private fun saveProduct() {
        if (etValue.text.isNullOrEmpty() || etDesc.text.isNullOrEmpty() ||
                etDiscount.text.isNullOrEmpty() || etAmount.text.isNullOrEmpty() ||
                etName.text.isNullOrEmpty()) {
            showWarning(R.string.warning_empty_fields)
            return
        }
        product?.amount = (etAmount.text).toString().toLong()
        product?.description = etDesc.text.toString()
        product?.value = etValue.getValue()
        product?.name = etValue.text.toString()
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                if (e != null) {
                    showWarning(e.message!!)
                }
            }

            override fun onImagesPicked(files: MutableList<File>, p1: EasyImage.ImageSource?, type: Int) {
                compressImage(files[0])
            }

            override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {
                Log.d("EasyImage", "cancelled")
            }
        })
    }


    private fun compressImage(file: File) {
        showProgress()
        ImageUtil.compressImage(file).applySchedulers().subscribe(
                {
                    uploadImage(it)
                },
                {
                    closeProgress()
                    handleException(it)
                }
        )
    }

    private fun uploadImage(file: String) {
        ImageService.uploadImage(file).applySchedulers().subscribe(
                {
                    setupImages(it)
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

    private fun setupImages(response: CloudinaryResponse) {
        product?.picture = response.url!!
        imPicture.load(response.url!!) { request -> request.fit() }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (!grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            alert(getString(R.string.disclaimer_camera_usage),
                    getString(R.string.error_title)) {
                yesButton {
                    PermissionUtils.requestCameraPermission(getActivity())
                }
            }.show()
        }
    }

}
