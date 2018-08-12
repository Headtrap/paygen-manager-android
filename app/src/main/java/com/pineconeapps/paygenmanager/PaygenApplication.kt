package com.pineconeapps.paygenmanager

import android.app.Application
import com.pineconeapps.paygenmanager.util.UserInfo


val prefs: UserInfo by lazy {
    PaygenApplication.prefs
}

class PaygenApplication : Application(){

    companion object {
        lateinit var prefs: UserInfo
    }

    override fun onCreate() {
        super.onCreate()
        prefs = UserInfo(applicationContext)

    }
}