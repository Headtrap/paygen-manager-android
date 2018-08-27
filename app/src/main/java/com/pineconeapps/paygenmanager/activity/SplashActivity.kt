package com.pineconeapps.paygenmanager.activity

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.prefs
import org.jetbrains.anko.startActivity

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({
            checkStart()
        }, 2100)
    }

    private fun checkStart() {
        when {
            prefs.token == "" -> startActivity<LoginActivity>()
            else -> startActivity<MainActivity>()
        }
        finish()
    }
}
