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

    private val _emailValid: SingleLiveEvent<String> = SingleLiveEvent()
    val emailValid: LiveData<String> get() = _emailValid

    private val _emailInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val emailInvalid: LiveData<String> get() = _emailInvalid

    private val _networkInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val networkInvalid: LiveData<String> get() = _networkInvalid

    fun requestEmailAuth(email: HashMap<String, String>) {
        if (!RegularExpressionUtil.validCheck(RegularExpressionUtil.Regex.EMAIL, email["email"])) {
            _emailInvalid.postValue("이메일 형식이 잘못되었습니다.")
        } else {
            compositeDisposable.add(signUpRepository.authRequest(email)
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
}