package com.example.dbmasterandroid.ui.export

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.RegularExpressionUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataExportViewModel(
        private val context: Context,
        private val tableRepository: TableRepository
): BaseViewModel() {

    private val _exportInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val exportInvalid: LiveData<String> get() = _exportInvalid

    private val _exportClear: SingleLiveEvent<String> = SingleLiveEvent()
    val exportClear: LiveData<String> get() = _exportClear

    fun exportTable(email: String) {
        when (RegularExpressionUtil.validCheck(RegularExpressionUtil.Regex.EMAIL, email)) {
            /* 이메일 주소가 형식에 맞지 않을때 */
            false->_exportInvalid.postValue("이메일 형식이 잘못되었습니다.")
            /* 이메일 주소가 형식에 맞을 때 */
            true->{
                val emailInfo = HashMap<String, String>()
                emailInfo["name"] = PreferenceUtil(context).getName("dbName", "DB Master")
                emailInfo["tableName"] = PreferenceUtil(context).getName("tableName", "DB Master")
                emailInfo["email"] = email

                compositeDisposable.add(tableRepository.exportTable(emailInfo)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { startLoadingIndicator() }
                        .doOnSuccess { stopLoadingIndicator() }
                        .doOnError { stopLoadingIndicator() }
                        .subscribe({ result->
                            when (result.result) {
                                "S01"->_exportClear.postValue(result.message)
                                "E01"->_exportInvalid.postValue("처리과정에 오류가 발생하였습니다.")
                            }
                        }, { error->
                            error.printStackTrace()
                            _exportInvalid.postValue("네트워크 오류가 발생하였습니다.")
                        })
                )
            }
        }
    }
}