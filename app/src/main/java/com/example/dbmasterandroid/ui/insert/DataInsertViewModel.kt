package com.example.dbmasterandroid.ui.insert

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataInsertViewModel(
        private val context: Context,
        private val tableRepository: TableRepository
): BaseViewModel() {

    private val tableInfoList = ArrayList<HashMap<String, String>>()

    private val _tableInfoValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableInfoValid: LiveData<Any> get() = _tableInfoValid

    private val _networkInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val networkInvalid: LiveData<String> get() = _networkInvalid

    private val _insertValid: SingleLiveEvent<String> = SingleLiveEvent()
    val insertValid: LiveData<String> get() = _insertValid

    private val _insertInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val insertInvalid: LiveData<String> get() = _insertInvalid

    /* Insert 어떻게 Parameter 던지는지 물어봐야 함. */
    fun insertData(dataList: List<String>) {
        val rowData = HashMap<String, String>()

        val query = makeQueryString(dataList)
        rowData["name"] = PreferenceUtil(context).getName("dbName", "DB Master")
        rowData["tableName"] = PreferenceUtil(context).getName("tableName", "DB Master")
        rowData["insert"] = query

        compositeDisposable.add(tableRepository.insertRowData(rowData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { startLoadingIndicator() }
                .doOnSuccess { stopLoadingIndicator() }
                .doOnError { stopLoadingIndicator() }
                .subscribe({
                    when(it.result) {
                        "S01" -> _insertValid.postValue(it.message)
                        else -> _insertInvalid.postValue(it.message)
                    }
                }, {
                    it.printStackTrace()
                    _networkInvalid.postValue("네트워크에 문제가 발생했습니다.")
                })
        )
    }

    fun getTableInfo() {
        val tableInfo = HashMap<String, String>()
        tableInfo["name"] = PreferenceUtil(context).getName("dbName", "DB Master")
        tableInfo["tableName"] = PreferenceUtil(context).getName("tableName", "DB Master")

        compositeDisposable.add(tableRepository.getTableInfo(tableInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { startLoadingIndicator() }
                .doOnSuccess { stopLoadingIndicator() }
                .doOnError { stopLoadingIndicator() }
                .subscribe({ result->
                    _tableInfoValid.call()
                    tableInfoList.addAll(result.value)
                }, { error->
                    error.printStackTrace()
                    _networkInvalid.postValue("네트워크에 문제가 발생하였습니다.")
                })
        )
    }

    fun getTableInfoListSize(): Int = tableInfoList.size
    fun getTableInfoListItem(position: Int): HashMap<String, String> = tableInfoList[position]

    private fun makeQueryString(dataList: List<String>): String {
        var query = ""
        for (index in dataList.indices) {
            val data = dataList[index]
            query += if (index != dataList.size - 1) {
                "$data, "
            } else {
                data
            }
        }
        Log.d("INSERT DATA", query)
        return query
    }
}