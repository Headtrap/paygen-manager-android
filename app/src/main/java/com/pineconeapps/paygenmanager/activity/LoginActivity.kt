package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.entity.LoginDTO
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.AccessService
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import java.security.Provider

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupActionBar()
        btLogin.setOnClickListener { validate() }
        tvDisclaimerRegister.setOnClickListener { startActivity<RegisterActivity>() }
    }

    private fun validate() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            showWarning(getString(R.string.warning_empty_fields))
            return
        }
        showProgress()
        when {
            rbAdmin.isChecked -> AccessService.validateProvider(email, password).applySchedulers().subscribe(
                    {
                        handleLogin(it)
                    },
                    {
                        closeProgress()
                        handleException(it)
                    },
                    {
                        closeProgress()
                    }
            )
            rbEmployees.isChecked -> AccessService.validateEmployee(email, password).applySchedulers().subscribe(
                    {
                        handleLogin(it)
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

    private fun handleLogin(dto: LoginDTO) {
        prefs.token = dto.token
        prefs.providerId = dto.providerId
        prefs.userId = dto.userId
        startActivity<MainActivity>()
    }


}