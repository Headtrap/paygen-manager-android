package com.pineconeapps.paygenmanager.util

import android.content.Context
import android.content.SharedPreferences
import com.firebase.ui.auth.AuthUI
import com.pineconeapps.paygenmanager.PaygenApplication.Companion.prefs
import com.pineconeapps.paygenmanager.entity.User
import com.pineconeapps.paygenmanager.util.Constants.PREF_EMAIL
import com.pineconeapps.paygenmanager.util.Constants.PREF_GOOGLE
import com.pineconeapps.paygenmanager.util.Constants.PREF_ID
import com.pineconeapps.paygenmanager.util.Constants.PREF_KEY
import com.pineconeapps.paygenmanager.util.Constants.PREF_PROVIDER_ID

/**
 * Created by Gustavo on 12/26/2017.
 */
class UserInfo(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_KEY, 0)

    companion object {
        fun saveUserLocally(user: User, google: Boolean) {
            prefs.userEmail = user.email
            prefs.token = user.id
            prefs.googleSignIn = google
        }

        fun clearData(context: Context) {
            AuthUI.getInstance()
                    .signOut(context)
                    .addOnCompleteListener {
                    }
            prefs.userEmail = ""
            prefs.token = ""
            prefs.googleSignIn = false
        }
    }

    var token: String
        get() = prefs.getString(PREF_ID, "5b34409e27039805549d3951")
        set(value) = prefs.edit().putString(PREF_ID, value).apply()

    var userEmail: String
        get() = prefs.getString(PREF_EMAIL, "")
        set(value) = prefs.edit().putString(PREF_EMAIL, value).apply()

    var googleSignIn: Boolean
        get() = prefs.getBoolean(PREF_GOOGLE, false)
        set(value) = prefs.edit().putBoolean(PREF_GOOGLE, value).apply()

    var providerId: String
        get() = prefs.getString(PREF_PROVIDER_ID, "5b5fcc2314e01300047c9ec9")
        set(value) = prefs.edit().putString(PREF_PROVIDER_ID, value).apply()
}