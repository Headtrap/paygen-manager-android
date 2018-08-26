package com.pineconeapps.paygenmanager.util

import android.content.Context
import android.content.SharedPreferences
import com.pineconeapps.paygenmanager.PaygenApplication.Companion.prefs
import com.pineconeapps.paygenmanager.entity.User
import com.pineconeapps.paygenmanager.util.Constants.PREF_ID
import com.pineconeapps.paygenmanager.util.Constants.PREF_KEY
import com.pineconeapps.paygenmanager.util.Constants.PREF_PICTURE
import com.pineconeapps.paygenmanager.util.Constants.PREF_PROVIDER_ID
import com.pineconeapps.paygenmanager.util.Constants.PREF_TOKEN
import com.pineconeapps.paygenmanager.util.Constants.PREF_USERNAME

/**
 * Created by Gustavo on 12/26/2017.
 */
class UserInfo(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_KEY, 0)

    companion object {

        fun clearData() {
            prefs.userId = ""
            prefs.token = ""
            prefs.providerId = ""
            prefs.userName = ""
            prefs.picture = ""
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

    var picture: String
        get() = prefs.getString(PREF_PICTURE, "")
        set(value) = prefs.edit().putString(PREF_PICTURE, value).apply()

    var userName: String
        get() = prefs.getString(PREF_USERNAME, "")
        set(value) = prefs.edit().putString(PREF_USERNAME, value).apply()
}