package com.pineconeapps.paygenmanager.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.entity.CloudinaryResponse
import com.pineconeapps.paygenmanager.entity.dto.ImagesDTO
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.ImageService
import com.pineconeapps.paygenmanager.service.ProviderService
import com.pineconeapps.paygenmanager.util.ImageUtil.load
import com.pineconeapps.paygenmanager.util.PermissionUtils
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_image.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.ByteArrayOutputStream
import java.io.File


class ImageActivity : BaseActivity() {
    private val banner = 1
    private val logo = 2

    private lateinit var urlBanner: String
    private lateinit var urlLogo: String

    private var imagesChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        setupActionBar()
        EasyImage.configuration(this).setAllowMultiplePickInGallery(false)
        imBanner.setOnClickListener { pickImage(banner) }
        imLogo.setOnClickListener { pickImage(logo) }
        btSave.setOnClickListener { sendImages() }

        getImages()
        imBanner.load("") { request -> request.fit() }
        imLogo.load("") { request -> request.fit() }

    }

    private fun getImages() {
        showProgress()
        ProviderService.getImages(prefs.providerId).applySchedulers().subscribe(
                {
                    setImages(it)
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

    private fun sendImages() {
        if (!imagesChanged) {
            showWarning(getString(R.string.warning_no_images_changed))
            return
        }
        showProgress()
        ProviderService.setImages(ImagesDTO(urlBanner, urlLogo), prefs.providerId).applySchedulers().subscribe(
                {
                    alert(it, getString(R.string.title_success)) { yesButton { finish() } }.show()
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

    override fun onStart() {
        super.onStart()

        if (!PermissionUtils.checkStoragePermission(getActivity())) {
            PermissionUtils.requestStoragePermission(getActivity())
        }
    }

    private fun pickImage(type: Int) {
        EasyImage.openGallery(this, type)
    }

    private fun uploadImage(file: String, type: Int) {
        ImageService.uploadImage(file).applySchedulers().subscribe(
                {
                    setupImages(it, type)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                if (e != null) {
                    showWarning(e.message!!)
                }
            }

            override fun onImagesPicked(files: MutableList<File>, p1: EasyImage.ImageSource?, type: Int) {
                getImageAsString(files[0], type)
            }

            override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {
                Log.d("EasyImage", "cancelled")
            }
        })
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

    private fun getImageAsString(file: File, type: Int) {
        showProgress()
        getBitmap(file).applySchedulers().subscribe(
                {
                    uploadImage(it, type)
                },
                {
                    closeProgress()
                    handleException(it)
                }
        )
    }

    private fun setupImages(response: CloudinaryResponse, type: Int) {
        imagesChanged = true
        when (type) {
            banner -> {
                urlBanner = response.url!!
                imBanner.load(urlBanner) { request -> request.fit() }
            }
            logo -> {
                urlLogo = response.url!!
                imLogo.load(urlLogo) { request -> request.resize(200, 200).centerInside() }
            }
        }
    }

    private fun getBitmap(file: File): Observable<String> {
        return Observable.create { subscriber ->
            try {
                val bitmap = BitmapFactory.decodeFile(file.path)
                val bao = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bao)
                val ba = bao.toByteArray()
                val string = Base64.encodeToString(ba, Base64.DEFAULT)
                subscriber.onNext("data:image/jpeg;base64,$string")
            } catch (e: Throwable) {
                subscriber.onError(e)
            }
        }
    }

    private fun setImages(images: ImagesDTO) {
        urlBanner = images.banner
        urlLogo = images.logo
        imBanner.load(images.banner) { request -> request.fit() }
        imLogo.load(images.logo) { request -> request.resize(200, 200).centerInside() }
    }
}
