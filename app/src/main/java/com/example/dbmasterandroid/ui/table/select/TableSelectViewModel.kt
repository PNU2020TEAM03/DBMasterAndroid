package com.example.dbmasterandroid.ui.table.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.TableRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TableSelectViewModel(
        private val tableRepository: TableRepository
) : BaseViewModel() {

    private val tableList = ArrayList<String>()

    private val _tableListLiveData = MutableLiveData<List<String>>()
    val tableListLiveData: LiveData<List<String>> get() = _tableListLiveData

    private val _tableListSizeLiveData = MutableLiveData<Int>()
    val tableListSizeLiveData: LiveData<Int> get() = _tableListSizeLiveData

    fun getAllTableList(name: String) {
        val dbName = HashMap<String, String>()
        dbName["name"] = name

        if (tableList.isEmpty()) {
            compositeDisposable.add(tableRepository.getAllTableList(dbName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { startLoadingIndicator() }
                    .doOnSuccess { stopLoadingIndicator() }
                    .doOnError { stopLoadingIndicator() }
                    .subscribe({ result ->
                        tableList.addAll(result.value)
                        _tableListLiveData.postValue(result.value)
                        _tableListSizeLiveData.postValue(tableList.size)
                    }, { t ->
                        t.printStackTrace()
                    })
            )
        }

    }

    fun getTableSize(): Int = tableList.size
    fun getTableItem(position: Int): String = tableList[position]
}