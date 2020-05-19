package com.example.dbmasterandroid.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dbmasterandroid.utils.PreferenceUtil

class MainViewModel(
        private val context: Context
): ViewModel() {

    private val _userNameLiveData: MutableLiveData<String> = MutableLiveData()
    val userNameLiveData: LiveData<String> get() = _userNameLiveData

    fun getUserName() {
        val userName = PreferenceUtil(context).getName("dbName", "DB Master")
        _userNameLiveData.postValue(userName)
    }
}