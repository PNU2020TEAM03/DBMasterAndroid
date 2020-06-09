package com.example.dbmasterandroid.ui.setting.drop

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SettingDropViewModel(
        private val tableRepository: TableRepository,
        private val context: Context
): BaseViewModel() {

    private val _tableDropValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableDropValid: LiveData<Any> get() = _tableDropValid

    private val _tableDropInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val tableDropInvalid: LiveData<String> get() = _tableDropInvalid

    private val _tableDropComplete: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableDropComplete: LiveData<Any> get() = _tableDropComplete

    fun checkTableNameDropValid(tableName: String) {
        val currentName = PreferenceUtil(context).getName("tableName", "DB Master")

        if (currentName == tableName) {
            _tableDropValid.call()
        } else {
            _tableDropInvalid.postValue("테이블 이름이 일치하지 않습니다.")
        }
    }

    fun tableDrop() {
        val tableInfo = HashMap<String, String>()

        tableInfo["name"] = PreferenceUtil(context).getName("dbName", "DB Master")
        tableInfo["tableName"] = PreferenceUtil(context).getName("tableName", "DB Master")

        compositeDisposable.add(tableRepository.dropTable(tableInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { startLoadingIndicator() }
                .doOnSuccess { stopLoadingIndicator() }
                .doOnError { stopLoadingIndicator() }
                .timeout(5, TimeUnit.SECONDS)
                .subscribe({
                    _tableDropComplete.call()
                }, {
                    it.printStackTrace()
                    _tableDropInvalid.postValue("테이블 삭제에 실패했습니다.")
                })
        )
    }
}