package com.example.dbmasterandroid.ui.setting.tablename

import android.content.Context
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.utils.PreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SettingTableViewModel(
        private val tableRepository: TableRepository,
        private val context: Context
): BaseViewModel() {

    fun renameTable(tableName: String) {
        val nameInfo = HashMap<String, String>()

        nameInfo["name"] = PreferenceUtil(context).getName("name", "DB Master")
        nameInfo["tableName"] = PreferenceUtil(context).getName("tableName", "DB Master")
        nameInfo["newName"] = tableName

        compositeDisposable.add(tableRepository.renameTable(nameInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { startLoadingIndicator() }
                .doOnSuccess { stopLoadingIndicator() }
                .doOnError { stopLoadingIndicator() }
                .subscribe({ result ->

                }, {

                })
        )
    }
}