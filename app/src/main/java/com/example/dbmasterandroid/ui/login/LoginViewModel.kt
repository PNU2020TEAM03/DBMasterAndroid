package com.example.dbmasterandroid.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dbmasterandroid.MainActivityApplication
import com.example.dbmasterandroid.data.ConnectionRepository
import com.example.dbmasterandroid.utils.SingleLiveEvent
import com.loopj.android.http.RequestParams
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LoginViewModel(
        private val connectionRepository: ConnectionRepository,
        private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private val _networkInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val networkInvalid: LiveData<Any> get() = _networkInvalid

    private val _idInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val idInvalid: LiveData<Any> get() = _idInvalid

    private val _connectionValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val connectionValid: LiveData<Any> get() = _connectionValid

    private val _connectionInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val connectionInvalid: LiveData<Any> get() = _connectionInvalid

    fun connect(name: String, pw: String) {
        val user = HashMap<String, String>()
        user["name"] = name
        user["pw"] = pw

        compositeDisposable.add(connectionRepository.connectionCheck(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5, TimeUnit.SECONDS)
                .subscribe({ result ->
                    if (result.idValid == "available") {
                        if (result.connectionValid == "available") {
                            _connectionValid.call()
                            MainActivityApplication.preferences.setName("dbName", name)
                        } else {
                            _connectionInvalid.call()
                        }
                    } else {
                        _idInvalid.call()
                    }
                }, { t ->
                    t.printStackTrace()
                    _networkInvalid.call()
                })
        )
    }
}