package com.example.dbmasterandroid.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
        private val tableRepository: TableRepository,
        private val compositeDisposable: CompositeDisposable,
        private val context: Context
): ViewModel() {

    private val _startLoadingLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val startLoadingLiveData: LiveData<Any> get() = _startLoadingLiveData

    private val _stopLoadingLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val stopLoadingLiveData: LiveData<Any> get() = _stopLoadingLiveData

    fun getUserName(): String {
        return PreferenceUtil(context).getName("dbName", "DB Master")
    }

    fun getTableName(): String {
        return PreferenceUtil(context).getName("tableName", "DB Master")
    }

    fun getAllTableData() {
        val name = HashMap<String, String>()
        name["name"] = getUserName()
        name["tableName"] = getTableName()

        compositeDisposable.add(tableRepository.getAllTableData(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _startLoadingLiveData.call() }
                .doOnSuccess { _stopLoadingLiveData.call() }
                .doOnError { _stopLoadingLiveData.call() }
                .subscribe({
                    Log.e("MAIN VIEW MODEL", "$it")
                }, {
                    it.printStackTrace()
                })
        )
    }
}