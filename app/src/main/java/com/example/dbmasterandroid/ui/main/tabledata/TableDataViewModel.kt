package com.example.dbmasterandroid.ui.main.tabledata

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.ColumnRepository
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TableDataViewModel(
        private val columnRepository: ColumnRepository,
        private val context: Context
): BaseViewModel() {

    private val tableAllDataList = ArrayList<HashMap<String, String>>()

    private val _tableDataListLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableDataListLiveData: LiveData<Any> get() = _tableDataListLiveData

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
                        tableAllDataList.addAll(it.value)
                        _tableDataListLiveData.call()
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

    fun getTableListSize(): Int = tableAllDataList.size
    fun getTableListItem(position: Int) = tableAllDataList[position]
}