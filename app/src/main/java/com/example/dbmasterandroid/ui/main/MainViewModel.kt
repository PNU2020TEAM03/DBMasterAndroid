package com.example.dbmasterandroid.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dbmasterandroid.data.ColumnRepository
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.data.dto.TableSelectAllDTO
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
        private val tableRepository: TableRepository,
        private val columnRepository: ColumnRepository,
        private val compositeDisposable: CompositeDisposable,
        private val context: Context
): ViewModel() {

    private var columnInfoList = ArrayList<HashMap<String, String>>()

    private val _columnInfoListUpdateLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val columnInfoListUpdateLiveData: LiveData<Any> get() = _columnInfoListUpdateLiveData

    private val _startLoadingLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val startLoadingLiveData: LiveData<Any> get() = _startLoadingLiveData

    private val _stopLoadingLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val stopLoadingLiveData: LiveData<Any> get() = _stopLoadingLiveData

    private val _tableNameLiveData: SingleLiveEvent<String> = SingleLiveEvent()
    val tableNameLiveData: LiveData<String> get() = _tableNameLiveData

    private val _tableAllDataLiveData: SingleLiveEvent<TableSelectAllDTO> = SingleLiveEvent()
    val tableAllDataLiveData: LiveData<TableSelectAllDTO> get() = _tableAllDataLiveData

    fun getUserName(): String {
        return PreferenceUtil(context).getName("dbName", "DB Master")
    }

    fun getTableName(): String {
        val tableName = PreferenceUtil(context).getName("tableName", "DB Master")
        _tableNameLiveData.postValue(tableName)
        return tableName
    }

    fun getAllTableData() {
        val name = HashMap<String, String>()
        name["name"] = getUserName()
        name["tableName"] = getTableName()

        compositeDisposable.add(columnRepository.getAllTableData(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _startLoadingLiveData.call()
                    getTableInfo()
                }
                .doOnSuccess { _stopLoadingLiveData.call() }
                .doOnError { _stopLoadingLiveData.call() }
                .subscribe({
                    _tableAllDataLiveData.postValue(it)
                    Log.e("MAIN VIEW MODEL", "$it")
                }, {
                    it.printStackTrace()
                })
        )
    }

    private fun getTableInfo() {
        val name = HashMap<String, String>()
        name["name"] = getUserName()
        name["tableName"] = getTableName()
        compositeDisposable.add(tableRepository.getTableInfo(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    columnInfoList = it.value as ArrayList<HashMap<String, String>>
                    _columnInfoListUpdateLiveData.call()
                    Log.e("MAIN GET TABLE", "$columnInfoList")
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun getColumnInfoListSize(): Int = columnInfoList.size
    fun getColumnInfoListItem(position: Int): HashMap<String, String> = columnInfoList[position]
}