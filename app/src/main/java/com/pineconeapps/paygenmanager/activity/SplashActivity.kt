package com.pineconeapps.paygenmanager.activity

import android.app.Activity
import android.os.Bundle
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.prefs
import org.jetbrains.anko.startActivity

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        when {
            prefs.token == "" -> startActivity<LoginActivity>()
            else -> startActivity<MainActivity>()
        }
    }
}
