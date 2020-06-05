package com.example.dbmasterandroid.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dbmasterandroid.base.BaseViewModel

/* TODO MVVM ViewModel Part */
class SplashViewModel : BaseViewModel() {

    private val _splashNavigationModeLiveData = MutableLiveData<Int>()
    val splashNavigationModeLiveData: LiveData<Int> get() = _splashNavigationModeLiveData

    fun getSplashNavigationMode() {
        /* TODO Splash to other Screens Navigation Mode */
        _splashNavigationModeLiveData.postValue(1)
    }
}