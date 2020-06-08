package com.example.dbmasterandroid.ui.setting.drop

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent

class SettingDropViewModel(
        private val context: Context
): BaseViewModel() {

    private val _tableDropValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableDropValid: LiveData<Any> get() = _tableDropValid

    private val _tableDropInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val tableDropInvalid: LiveData<String> get() = _tableDropInvalid

    fun checkTableNameDropValid(tableName: String) {
        val currentName = PreferenceUtil(context).getName("tableName", "DB Master")

        if (currentName == tableName) {
            _tableDropValid.call()
        } else {
            _tableDropInvalid.postValue("테이블 이름이 일치하지 않습니다.")
        }
    }
}