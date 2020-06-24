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
    private val tableSortedDataList = ArrayList<HashMap<String, String>>()

    private val _tableDataListLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableDataListLiveData: LiveData<Any> get() = _tableDataListLiveData

    private val _tableDataListAfterDelete: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableDataListAfterDelete: LiveData<Any> get() = _tableDataListAfterDelete

    private val _tableSearchComplete: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableSearchComplete: LiveData<Any> get() = _tableSearchComplete

    private val _networkInvalidLiveData: SingleLiveEvent<String> = SingleLiveEvent()
    val networkInvalidLiveData: LiveData<String> get() = _networkInvalidLiveData

    private val _tableSortComplete: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableSortComplete: LiveData<Any> get() = _tableSortComplete

    private val _dataDeleteComplete: SingleLiveEvent<String> = SingleLiveEvent()
    val dataDeleteComplete: LiveData<String> get() = _dataDeleteComplete

    private val _dataDeleteInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val dataDeleteInvalid: LiveData<String> get() = _dataDeleteInvalid

    private var tablePrimaryKey = ""

    private fun getTableInfo() {
        val info = HashMap<String, String>()
        info["tableName"] = getTableName()
        info["name"] = getUserName()

        compositeDisposable.add(tableRepository.getTableInfo(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { stopLoadingIndicator() }
                .doOnError { stopLoadingIndicator() }
                .subscribe({
                    getPrimaryKey(it.value)
                }, {
                    it.printStackTrace()
                    _networkInvalidLiveData.postValue("네트워크에 문제가 발생했습니다.")
                })
        )
    }

    fun deleteTableData(primaryKey: String, primaryData: String) {
        val deleteInfo = HashMap<String, String>()

        deleteInfo["name"] = getUserName()
        deleteInfo["tableName"] = getTableName()
        deleteInfo["primary_key_name"] = primaryKey
        deleteInfo["primary_key_value"] = primaryData

        compositeDisposable.add(columnRepository.deleteData(deleteInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.result) {
                        "S01"->_dataDeleteComplete.postValue(it.message)
                        else->_dataDeleteInvalid.postValue(it.message)
                    }
                }, {
                    it.printStackTrace()
                    _networkInvalidLiveData.postValue("네트워크에 문제가 발생했습니다.")
                })
        )
    }

    fun sortedTableData(columnName: String, direction: String) {
        val tableSortInfo = HashMap<String, String>()

        tableSortedDataList.clear()

        tableSortInfo["name"] = getUserName()
        tableSortInfo["tableName"] = getTableName()
        tableSortInfo["sortColumn"] = columnName
        tableSortInfo["direction"] = direction

        compositeDisposable.add(tableRepository.sortTable(tableSortInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { startLoadingIndicator() }
                .doOnSuccess { stopLoadingIndicator() }
                .doOnError { stopLoadingIndicator() }
                .subscribe({
                    tableSortedDataList.addAll(it.value)
                    _tableSortComplete.call()
                }, {
                    it.printStackTrace()
                    _networkInvalidLiveData.postValue("네트워크에 문제가 발생하였습니다.")
                })
        )

    }

    fun searchTableData(keyWord: String) {
        val keywordInfo = HashMap<String, String>()

        tableSearchDataList.clear()

        keywordInfo["name"] = getUserName()
        keywordInfo["tableName"] = getTableName()
        keywordInfo["keyword"] = keyWord

        compositeDisposable.add(tableRepository.searchTableData(keywordInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { startLoadingIndicator() }
                .doOnSuccess { stopLoadingIndicator() }
                .doOnError { stopLoadingIndicator() }
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

    fun getAllTableDataAfterDelete() {
        val name = HashMap<String, String>()

        name["name"] = getUserName()
        name["tableName"] = getTableName()

        compositeDisposable.add(columnRepository.getAllTableData(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    startLoadingIndicator()
                    getTableInfo()
                }
                .subscribe({
                    if (it.value != null) {
                        tableAllDataList.addAll(it.value)
                        _tableDataListAfterDelete.call()
                    } else {
                        _tableDataListAfterDelete.call()
                    }
                    Log.e("MAIN VIEW MODEL", "$it")
                }, {
                    it.printStackTrace()
                    _networkInvalidLiveData.postValue("네트워크에 문제가 발생했습니다.")
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
                    .doOnSubscribe {
                        startLoadingIndicator()
                        getTableInfo()
                    }
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
                        _networkInvalidLiveData.postValue("네트워크에 문제가 발생했습니다.")
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

    private fun getPrimaryKey(list: List<HashMap<String, String>>) {
        for (item in list) {
            if (item["ispk"] == "Y") {
                tablePrimaryKey = item["columnName"].toString()
            }
        }
    }

    fun getTablePrimaryKey(): String = tablePrimaryKey

    fun getSearchTableListSize(): Int = tableSearchDataList.size
    fun getSearchTableListItem(position: Int) = tableSearchDataList[position]

    fun getTableListSize(): Int = tableAllDataList.size
    fun getTableListItem(position: Int) = tableAllDataList[position]

    fun getSortedTableListSize(): Int = tableSortedDataList.size
    fun getSortedTableListItem(position: Int) = tableSortedDataList[position]

    fun getTableColumnNames(): MutableSet<String> = tableAllDataList[0].keys

    fun clearList() {
        tableAllDataList.clear()
    }
}