package com.pineconeapps.paygenmanager.util

import android.content.Context
import android.content.SharedPreferences
import com.pineconeapps.paygenmanager.PaygenApplication.Companion.prefs
import com.pineconeapps.paygenmanager.entity.User
import com.pineconeapps.paygenmanager.util.Constants.PREF_ID
import com.pineconeapps.paygenmanager.util.Constants.PREF_KEY
import com.pineconeapps.paygenmanager.util.Constants.PREF_PROVIDER_ID
import com.pineconeapps.paygenmanager.util.Constants.PREF_TOKEN

/**
 * Created by Gustavo on 12/26/2017.
 */
class UserInfo(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_KEY, 0)

    companion object {
        fun saveUserLocally(user: User, google: Boolean) {
            prefs.userId = user.email
            prefs.token = user.id
        }

        fun clearData() {
            prefs.userId = ""
            prefs.token = ""
            prefs.providerId = ""
        }
    }


    var token: String
        get() = prefs.getString(PREF_TOKEN, "")
        set(value) = prefs.edit().putString(Constants.PREF_TOKEN, value).apply()

    var userId: String
        get() = prefs.getString(PREF_ID, "")
        set(value) = prefs.edit().putString(PREF_ID, value).apply()


    var providerId: String
        get() = prefs.getString(PREF_PROVIDER_ID, "")
        set(value) = prefs.edit().putString(PREF_PROVIDER_ID, value).apply()
}