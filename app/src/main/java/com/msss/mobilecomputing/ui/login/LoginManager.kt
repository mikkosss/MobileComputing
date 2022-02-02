package com.msss.mobilecomputing.ui.login

import android.content.Context
import android.content.SharedPreferences

class LoginManager(var context: Context) {
    var pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, 0)
    var editor: SharedPreferences.Editor = pref.edit()

    init {
        createUser("admin","1234")
    }

    companion object {
        const val PREF_NAME: String = "user"
        const val KEY_NAME: String = "name"
        const val KEY_PASS: String = "pass"
    }

    fun createUser(
        name: String,
        pass: String
    ) {
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_PASS, pass)
        editor.apply()
    }

    fun checkCredentials(
        name: String,
        pass: String
    ): Boolean {
        return pref.getString(KEY_NAME, null).equals(name) && pref.getString(KEY_PASS, null).equals(pass)
    }

    fun displayUsername(): String? {
        return pref.getString(KEY_NAME, null)
    }

    fun changePass(
        oldPass: String,
        newPass: String
    ): Boolean {
        if (pref.getString(KEY_PASS, null).equals(oldPass)) {
            editor.putString(KEY_PASS, newPass)
            editor.apply()
            return true
        }
        else return false
    }

    fun userExist(
        name: String
    ): Boolean {
        return pref.getString(KEY_NAME, null).equals(name)
    }
}