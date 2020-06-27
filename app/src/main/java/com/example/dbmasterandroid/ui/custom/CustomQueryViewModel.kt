package com.example.dbmasterandroid.ui.custom

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.QueryRepository
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CustomQueryViewModel(
        private val context: Context,
        private val queryRepository: QueryRepository
): BaseViewModel() {

    private val customQueryResultList = ArrayList<HashMap<String, String>>()

    private val _queryComplete: SingleLiveEvent<Any> = SingleLiveEvent()
    val queryComplete: LiveData<Any> get() = _queryComplete

    private val _queryInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val queryInvalid: LiveData<String> get() = _queryInvalid

    private val _networkInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val networkInvalid: LiveData<String> get() = _networkInvalid

    fun customQuery(query: String) {
        val queryInfo = HashMap<String, String>()
        queryInfo["name"] = getUserName()
        queryInfo["tableName"] = getTableName()
        queryInfo["query"] = query

        compositeDisposable.add(queryRepository.queryCustom(queryInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { startLoadingIndicator() }
                .doOnSuccess { stopLoadingIndicator() }
                .doOnError { stopLoadingIndicator() }
                .subscribe({
                    when (it.result) {
                        "S01"->{
                            customQueryResultList.addAll(it.value)
                            _queryComplete.call()
                        }
                        else->{
                            _queryInvalid.postValue(it.message)
                        }
                    }
                }, {
                    it.printStackTrace()
                    _networkInvalid.postValue("네트워크에 문제가 발생했습니다.")
                })
        )
    }

    fun getCustomQueryResultListSize() = customQueryResultList.size
    fun getCustomQueryResultListItem(position: Int) = customQueryResultList[position]
    fun getColumnNames() = customQueryResultList[0].keys

    private fun getUserName(): String {
        return PreferenceUtil(context).getName("dbName", "DB Master")
    }

    private fun getTableName(): String {
        return PreferenceUtil(context).getName("tableName", "DB Master")
    }
}