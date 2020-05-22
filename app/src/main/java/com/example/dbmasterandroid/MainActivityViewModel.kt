package com.example.dbmasterandroid

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.dbmasterandroid.utils.PreferenceUtil

class MainActivityViewModel(
        private val context: Context
): ViewModel() {

    fun logout() {
        PreferenceUtil(context).deletePreference()
    }
}