package com.example.dbmasterandroid.ui.insert

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
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

    /* Insert 어떻게 Parameter 던지는지 물어봐야 함. */
    fun insertData(data: List<Any>) {

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
}