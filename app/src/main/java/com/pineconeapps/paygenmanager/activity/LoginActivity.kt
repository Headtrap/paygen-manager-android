package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.AccessService
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btLogin.setOnClickListener { validate() }
    }

    private fun validate() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            showWarning(getString(R.string.warning_empty_fields))
        }
        showProgress()
        AccessService.validateProvider(email, password).applySchedulers().subscribe(
                {
                    prefs.token = it.token
                    prefs.providerId = it.id
                    startActivity<MainActivity>()
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


}