package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.entity.User
import com.pineconeapps.paygenmanager.entity.dto.LoginDTO
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.AccessService
import com.pineconeapps.paygenmanager.util.UserInfo
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupActionBar()
        btLogin.setOnClickListener { validate() }
        tvDisclaimerRegister.setOnClickListener { startActivity<RegisterActivity>() }
    }

    override fun onStart() {
        super.onStart()
        UserInfo.clearData()
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

    //TODO relatorio por data

    private fun handleLogin(dto: LoginDTO) {
        prefs.token = dto.token
        prefs.providerId = dto.providerId
        prefs.userId = dto.userId
        prefs.picture = dto.picture
        prefs.userName = dto.userName

        when {
            dto.status == User.Status.PENDING -> handleStatusPending()
            else -> nextActivity()
        }
    }

    private fun handleStatusPending() {
        alert(getString(R.string.status_pending), getString(R.string.disclaimer_status_pending)) {
            yesButton { startActivity<ChangePasswordActivity>() }
        }.show()
    }

    private fun nextActivity() {
        startActivity(intentFor<MainActivity>().clearTask().clearTop())
        finish()
    }

}