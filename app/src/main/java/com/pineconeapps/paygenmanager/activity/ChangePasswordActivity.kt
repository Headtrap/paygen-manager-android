package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.AccessService
import kotlinx.android.synthetic.main.activity_change_password.*
import org.jetbrains.anko.*

class ChangePasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        setupActionBar()

        btSend.setOnClickListener { onClickSend() }
    }

    private fun onClickSend() {
        AccessService.changePassword(prefs.userId, etPassword.text.toString()).applySchedulers().subscribe(
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
        alert(message, getString(R.string.title_success)) {
            yesButton { nextActivity() }
        }.show()
    }

    private fun nextActivity() {
        startActivity(intentFor<MainActivity>().clearTask().clearTop())
        finish()
    }
}
