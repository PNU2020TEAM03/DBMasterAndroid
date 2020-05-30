package com.example.dbmasterandroid.ui.signup.emailauth

import androidx.lifecycle.LiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.SignUpRepository
import com.example.dbmasterandroid.utils.RegularExpressionUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SignUpEmailViewModel(
        private val signUpRepository: SignUpRepository
): BaseViewModel() {

    private var currentEmail = "ex@example.com"

    private val _emailValid: SingleLiveEvent<String> = SingleLiveEvent()
    val emailValid: LiveData<String> get() = _emailValid

    private val _emailInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val emailInvalid: LiveData<String> get() = _emailInvalid

    private val _networkInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val networkInvalid: LiveData<String> get() = _networkInvalid

    fun requestEmailAuth(email: String) {
        val emailInfo = HashMap<String, String>()
        emailInfo["email"] = email

        if (!RegularExpressionUtil.validCheck(RegularExpressionUtil.Regex.EMAIL, email)) {
            _emailInvalid.postValue("이메일 형식이 잘못되었습니다.")
        } else {
            currentEmail = email
            compositeDisposable.add(signUpRepository.authRequest(emailInfo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(5, TimeUnit.SECONDS)
                    .subscribe({
                        if (it.result == "S01") {
                            _emailValid.postValue(it.message)
                        } else {
                            _emailInvalid.postValue(it.message)
                        }
                    }, {
                        it.printStackTrace()
                        _networkInvalid.postValue("네트워크 문제가 발생하였습니다.")
                    })
            )
        }
    }

    fun checkEmailAuth(authNumber: Int) {
        val authInfo = HashMap<String, String>()
        authInfo["email"] = currentEmail
        authInfo["authNum"] = authNumber.toString()


    }
}