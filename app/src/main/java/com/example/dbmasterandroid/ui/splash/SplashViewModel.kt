package com.example.dbmasterandroid.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/* TODO MVVM ViewModel Part */
class SplashViewModel : ViewModel() {

    private val _splashNavigationModeLiveData = MutableLiveData<Int>()
    val splashNavigationModeLiveData: LiveData<Int> get() = _splashNavigationModeLiveData

    fun getSplashNavigationMode() {
        /* TODO Splash to other Screens Navigation Mode */
        _splashNavigationModeLiveData.postValue(1)
    }
}