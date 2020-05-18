package com.example.dbmasterandroid.ui.table.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TableSelectViewModel(
        private val tableRepository: TableRepository,
        private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private var tableList = ArrayList<String>()

    private val _tableListLiveData = MutableLiveData<List<String>>()
    val tableListLiveData: LiveData<List<String>> get() = _tableListLiveData

    private val _tableListSizeLiveData = MutableLiveData<Int>()
    val tableListSizeLiveData: LiveData<Int> get() = _tableListSizeLiveData

    private val _startLoadingLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val startLoadingLiveData: LiveData<Any> get() = _startLoadingLiveData

    private val _stopLoadingLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val stopLoadingLiveData: LiveData<Any> get() = _stopLoadingLiveData

    fun getAllTableList(name: String) {
        val dbName = HashMap<String, String>()
        dbName["name"] = name

        compositeDisposable.add(tableRepository.getAllTableList(dbName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _startLoadingLiveData.call() }
                .doOnSuccess { _stopLoadingLiveData.call() }
                .doOnError { _stopLoadingLiveData.call() }
                .subscribe({ result ->
                    tableList = result.value
                    _tableListLiveData.postValue(result.value)
                    _tableListSizeLiveData.postValue(tableList.size)
                }, { t ->
                    t.printStackTrace()
                })
        )
    }

    fun getTableSize(): Int = tableList.size

    fun getTableItem(position: Int): String = tableList[position]
}