package com.pineconeapps.paygenmanager

import android.app.Application
import android.content.Context
import com.pineconeapps.paygenmanager.util.UserInfo


val prefs: UserInfo by lazy {
    PaygenApplication.prefs
}

class PaygenApplication : Application() {

    companion object {
        lateinit var prefs: UserInfo
        private var instance: PaygenApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = UserInfo(applicationContext)

    }

}

