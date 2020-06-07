package com.example.dbmasterandroid.ui.setting.pw

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.SignUpRepository
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.RegularExpressionUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SettingPasswordViewModel(
        private val signUpRepository: SignUpRepository,
        private val context: Context
): BaseViewModel() {

    private val _passwordValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val passwordValid: LiveData<Any> get() = _passwordValid

    private val _passwordInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val passwordInvalid: LiveData<String> get() = _passwordInvalid

    fun checkPasswordValid(newPassword: String, newPasswordCheck: String, oldPassword: String) {
        if (newPassword == newPasswordCheck) {
            val valid = RegularExpressionUtil.validCheck(RegularExpressionUtil.Regex.PASSWORD, newPassword)
            if (valid) {
                changePassword(newPassword, oldPassword)
            } else {
                _passwordInvalid.postValue("사용할 수 없는 비밀번호입니다.")
            }
        } else if (newPassword == oldPassword){
            _passwordInvalid.postValue("새로운 비밀번호가 현재 비밀번호와 같습니다.")
        } else if (newPassword != newPasswordCheck) {
            _passwordInvalid.postValue("새로운 비밀번호가 일치하지 않습니다.")
        }
    }

    private fun changePassword(newPassword: String, oldPassword: String) {
        val passwordInfo = HashMap<String, String>()

        passwordInfo["name"] = PreferenceUtil(context).getName("dbName", "DB Master")
        passwordInfo["oldPw"] = oldPassword
        passwordInfo["newPw"] = newPassword

        compositeDisposable.add(signUpRepository.changePassword(passwordInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { startLoadingIndicator() }
                .doOnSuccess { stopLoadingIndicator() }
                .doOnError { stopLoadingIndicator() }
                .subscribe({ result ->
                    if (result.result == "S01") {
                        _passwordValid.call()
                    } else {
                        _passwordInvalid.postValue(result.message)
                    }
                }, {
                    it.printStackTrace()
                    _passwordInvalid.postValue("네트워크 문제가 발생하였습니다.")
                })
        )
    }
}