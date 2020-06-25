package com.example.dbmasterandroid.ui.update

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.ColumnRepository
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataUpdateViewModel(
        private val context: Context,
        private val columnRepository: ColumnRepository
): BaseViewModel() {

    private val _updateValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val updateValid: LiveData<Any> get() = _updateValid

    private val _updateInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val updateInvalid: LiveData<String> get() = _updateInvalid

    private val _networkInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val networkInvalid: LiveData<String> get() = _networkInvalid

    fun updateTableData(pkInfo: HashMap<String, String>, dataInfo: HashMap<String, String>) {
        val updateInfo = HashMap<String, String>()

        updateInfo["name"] = getUserName()
        updateInfo["tableName"] = getTableName()
        updateInfo["primary_key_name"] = pkInfo["pkName"].toString()
        updateInfo["primary_key_value"] = pkInfo["pkValue"].toString()
        updateInfo["update_column_name"] = dataInfo["columnName"].toString()
        updateInfo["update_value"] = dataInfo["value"].toString()

        compositeDisposable.add(columnRepository.updateData(updateInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { startLoadingIndicator() }
                .doOnSuccess { stopLoadingIndicator() }
                .doOnError { stopLoadingIndicator() }
                .subscribe({
                    when(it.result) {
                        "S01"->_updateValid.call()
                        else->_updateInvalid.postValue(it.message)
                    }
                }, {
                    it.printStackTrace()
                    _networkInvalid.postValue("네트워크에 문제가 발생했습니다.")
                })
        )

    }

    private fun getUserName(): String {
        return PreferenceUtil(context).getName("dbName", "DB Master")
    }

    private fun getTableName(): String {
        return PreferenceUtil(context).getName("tableName", "DB Master")
    }

}