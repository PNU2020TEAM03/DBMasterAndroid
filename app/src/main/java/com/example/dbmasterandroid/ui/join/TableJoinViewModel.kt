package com.example.dbmasterandroid.ui.join

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TableJoinViewModel(
        private val context: Context,
        private val tableRepository: TableRepository
): BaseViewModel() {

    private val joinTableInfo = ArrayList<HashMap<String, String>>()

    private val _tableJoinInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val tableJoinInvalid: LiveData<String> get() = _tableJoinInvalid

    private val _tableJoinComplete: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableJoinComplete: LiveData<Any> get() = _tableJoinComplete

    private val _tableNameList: SingleLiveEvent<List<String>> = SingleLiveEvent()
    val tableNameList: LiveData<List<String>> get() = _tableNameList

    private val _columnNameList: SingleLiveEvent<List<String>> = SingleLiveEvent()
    val columnNameList: LiveData<List<String>> get() = _columnNameList

    private val _networkInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val networkInvalid: LiveData<Any> get() = _networkInvalid

    fun getJoinTableListSize() = joinTableInfo.size
    fun getJoinTableListItem(position: Int) = joinTableInfo[position]

    fun getJoinTableColumnName() = joinTableInfo[0].keys

    fun tableJoin(targetTable: String, targetColumn: String) {
        val joinInfo = HashMap<String, String>()

        joinInfo["name"] = getUserName()
        joinInfo["tableName"] = getTableName()
        joinInfo["joinTable"] = targetTable
        joinInfo["joiningColumn"] = targetColumn

        compositeDisposable.add(tableRepository.joinTable(joinInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { startLoadingIndicator() }
                .doOnSuccess { stopLoadingIndicator() }
                .doOnError { stopLoadingIndicator() }
                .subscribe({
                    when (it.result) {
                        "S01"->{
                            joinTableInfo.addAll(it.value)
                            _tableJoinComplete.call()
                        }
                        else->{
                            _tableJoinInvalid.postValue("테이블 Join 에 실패했습니다.\n Join 할 수 있는 조건인지 다시 확인 해주세요.")
                        }
                    }
                }, {
                    it.printStackTrace()
                    _networkInvalid.postValue("네트워크에 문제가 발생했습니다.")
                })
        )
    }

    fun getAllTableName() {
        val name = HashMap<String, String>()

        name["name"] = getUserName()

        compositeDisposable.add(tableRepository.getAllTableList(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { startLoadingIndicator() }
                .doOnSuccess { stopLoadingIndicator() }
                .doOnError { stopLoadingIndicator() }
                .subscribe({
                    _tableNameList.postValue(it.value)
                }, {
                    it.printStackTrace()
                    _networkInvalid.postValue("네트워크에 문제가 발생했습니다.")
                })
        )
    }

    fun getTableInfo(tableName: String) {
        val info = HashMap<String, String>()
        info["name"] = getUserName()
        info["tableName"] = tableName

        compositeDisposable.add(tableRepository.getTableInfo(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { startLoadingIndicator() }
                .doOnSuccess { stopLoadingIndicator() }
                .doOnError { stopLoadingIndicator() }
                .subscribe({
                    val columnNameList = ArrayList<String>()
                    for (item in it.value) {
                        val columnName = item["columnName"].toString()
                        columnNameList.add(columnName)
                    }
                    _columnNameList.postValue(columnNameList)
                }, {
                    it.printStackTrace()
                    _networkInvalid.postValue("네트워크에 문제가 발생했습니다.")
                })
        )
    }

    private fun getUserName(): String {
        return PreferenceUtil(context).getName("dbName", "DB Master")
    }

    private fun getTableName(): String {
        return PreferenceUtil(context).getName("tableName", "DB Master")
    }

}