package com.example.dbmasterandroid.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {

    private val sharedPreferences: SharedPreferences
            = context.getSharedPreferences("names", Context.MODE_PRIVATE)

    fun getName(key: String, defValue: String): String
            = sharedPreferences.getString(key, defValue).toString()

    fun setName(key: String, name: String) {
        sharedPreferences.edit().putString(key, name).apply()
    }

    fun deletePreference() {
        sharedPreferences.edit().clear().apply()
    }
}