package com.example.dbmasterandroid.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dbmasterandroid.data.SignUpRepository
import com.example.dbmasterandroid.utils.RegularExpressionUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SignUpViewModel(
        private val signUpRepository: SignUpRepository,
        private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private val _networkInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val networkInvalid: LiveData<Any> get() = _networkInvalid

    private val _nameValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val nameValid: LiveData<Any> get() = _nameValid

    private val _nameInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val nameInvalid: LiveData<Any> get() = _nameInvalid

    private val _passwordValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val passwordValid: LiveData<Any> get() = _passwordValid

    private val _passwordInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val passwordInvalid: LiveData<Any> get() = _passwordInvalid

    private val _signUpInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val signUpInvalid: LiveData<Any> get() = _signUpInvalid

    private val _signUpValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val signUpValid: LiveData<Any> get() = _signUpValid

    var currentID: String? = null
    var currentPW: String? = null

    fun signUp() {
        if (currentID != null && currentPW != null) {
            val userMap = HashMap<String, String>()
            userMap["name"] = currentID!!
            userMap["pw"] = currentPW!!

            compositeDisposable.add(signUpRepository.signUp(userMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(5, TimeUnit.SECONDS)
                    .subscribe({ response ->
                        when (response.result) {
                            "E01", "E02", "E03" -> {
                                _signUpInvalid.call()
                            }
                            "S01" -> {
                                _signUpValid.call()
                            }
                        }
                    }, { t ->
                        Log.e("Sign Up ViewModel", "${t.message}")
                        _networkInvalid.call()
                    })
            )
        } else {
            _signUpInvalid.call()
        }
    }

    fun checkPasswordValid(password: String) {
        val valid = RegularExpressionUtil.validCheck(RegularExpressionUtil.Regex.PASSWORD, password)

        if (valid) {
            _passwordValid.call()
            currentPW = password
            Log.e("Sign Up View Model", "$currentID, $currentPW")
        } else {
            _passwordInvalid.call()
        }
    }

    fun checkNameValid(name: String?) {
        /* Database 이름 Regular Expression 검사 */
        val result: Boolean = RegularExpressionUtil.validCheck(RegularExpressionUtil.Regex.NAME, name)

        if (result) {
            val nameMap = HashMap<String, String>()
            nameMap["name"] = name!!
            compositeDisposable.add(signUpRepository.checkNameDuplication(nameMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(5, TimeUnit.SECONDS)
                    .subscribe({ response ->
                        when (response.result) {
                            "E01", "duplicate" -> {
                                _nameInvalid.call()
                            }
                            "available" -> {
                                currentID = name
                                _nameValid.call()
                                Log.e("Sign Up View Model", "$currentID, $currentPW")
                            }
                        }
                    }, { t ->
                        Log.e("Sign Up ViewModel", "${t.message}")
                        _networkInvalid.call()
                    })
            )
        } else {
            _nameInvalid.call()
        }
    }
}