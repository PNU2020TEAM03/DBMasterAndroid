package com.example.dbmasterandroid.ui.setting.tablename

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.RegularExpressionUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SettingNameViewModel(
        private val tableRepository: TableRepository,
        private val context: Context
): BaseViewModel() {

    private val _tableNameSetValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableNameSetValid: LiveData<Any> get() = _tableNameSetValid

    private val _tableNameSetInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val tableNameSetInvalid: LiveData<String> get() = _tableNameSetInvalid

    fun renameTable(tableName: String) {
        val nameInfo = HashMap<String, String>()

        nameInfo["name"] = PreferenceUtil(context).getName("dbName", "DB Master")
        nameInfo["tableName"] = PreferenceUtil(context).getName("tableName", "DB Master")
        nameInfo["newName"] = tableName

        when (RegularExpressionUtil.validCheck(RegularExpressionUtil.Regex.NAME, tableName)) {
            true->{
                compositeDisposable.add(tableRepository.renameTable(nameInfo)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { startLoadingIndicator() }
                        .doOnSuccess { stopLoadingIndicator() }
                        .doOnError { stopLoadingIndicator() }
                        .subscribe({ result ->
                            when (result.result) {
                                "S01"->{
                                    _tableNameSetValid.call()
                                }
                                else->_tableNameSetInvalid.postValue("현재 테이블이 등록되어있지 않습니다.")
                            }
                        }, {
                            it.printStackTrace()
                            _tableNameSetInvalid.postValue("네트워크 문제가 발생하였습니다.")
                        })
                )
            }
            else->{
                _tableNameSetInvalid.postValue("사용할 수 없는 테이블 이름입니다.")
            }
        }
    }
}