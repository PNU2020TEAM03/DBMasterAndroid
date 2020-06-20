package com.example.dbmasterandroid.ui.main.tabledata

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.ColumnRepository
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TableDataViewModel(
        private val columnRepository: ColumnRepository,
        private val tableRepository: TableRepository,
        private val context: Context
): BaseViewModel() {

    private val tableAllDataList = ArrayList<HashMap<String, String>>()
    private val tableSearchDataList = ArrayList<HashMap<String, String>>()

    private val _tableDataListLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableDataListLiveData: LiveData<Any> get() = _tableDataListLiveData

    private val _tableSearchComplete: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableSearchComplete: LiveData<Any> get() = _tableSearchComplete

    private val _networkInvalidLiveData: SingleLiveEvent<String> = SingleLiveEvent()
    val networkInvalidLiveData: LiveData<String> get() = _networkInvalidLiveData

    fun searchTableData(keyWord: String) {
        val keywordInfo = HashMap<String, String>()

        tableSearchDataList.clear()

        keywordInfo["name"] = getUserName()
        keywordInfo["tableName"] = getTableName()
        keywordInfo["keyword"] = keyWord

        compositeDisposable.add(tableRepository.searchTableData(keywordInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("Search Process", "$it")
                    tableSearchDataList.addAll(it.value)
                    _tableSearchComplete.call()
                }, { error ->
                    error.printStackTrace()
                    _networkInvalidLiveData.postValue("네트워크에 문제가 발생하였습니다.")
                })
        )
    }

    fun getAllTableData() {
        val name = HashMap<String, String>()

        name["name"] = getUserName()
        name["tableName"] = getTableName()

        if (tableAllDataList.isEmpty()) {
            compositeDisposable.add(columnRepository.getAllTableData(name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { startLoadingIndicator() }
                    .doOnSuccess { stopLoadingIndicator() }
                    .doOnError { stopLoadingIndicator() }
                    .subscribe({
                        if (it.value != null) {
                            tableAllDataList.addAll(it.value)
                            _tableDataListLiveData.call()
                        } else {
                            _tableDataListLiveData.call()
                        }
                        Log.e("MAIN VIEW MODEL", "$it")
                    }, {
                        it.printStackTrace()
                    })
            )
        }
    }

    private fun getUserName(): String {
        return PreferenceUtil(context).getName("dbName", "DB Master")
    }

    private fun getTableName(): String {
        return PreferenceUtil(context).getName("tableName", "DB Master")
    }

    fun getSearchTableListSize(): Int = tableSearchDataList.size
    fun getSearchTableListItem(position: Int) = tableSearchDataList[position]

    fun getTableListSize(): Int = tableAllDataList.size
    fun getTableListItem(position: Int) = tableAllDataList[position]

    fun getTableColumnNames(): MutableSet<String> = tableAllDataList[0].keys
}