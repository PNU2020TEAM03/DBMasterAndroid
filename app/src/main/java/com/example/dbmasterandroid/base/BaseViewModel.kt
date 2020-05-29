package com.example.dbmasterandroid.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    private val _startLoadingLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val startLoadingLiveData: LiveData<Any> get() = _startLoadingLiveData

    private val _stopLoadingLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val stopLoadingLiveData: LiveData<Any> get() = _stopLoadingLiveData

    val compositeDisposable = CompositeDisposable()

    fun startLoadingIndicator(){
        _startLoadingLiveData.call()
    }

    fun stopLoadingIndicator(){
        _stopLoadingLiveData.call()
    }
}