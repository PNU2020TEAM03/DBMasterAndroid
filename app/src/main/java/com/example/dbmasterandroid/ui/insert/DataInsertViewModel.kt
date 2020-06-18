package com.example.dbmasterandroid.ui.insert

import android.content.Context
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.utils.PreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataInsertViewModel(
        private val context: Context,
        private val tableRepository: TableRepository
): BaseViewModel() {

    fun getTableInfo() {
        val tableInfo = HashMap<String, String>()
        tableInfo["dbName"] = PreferenceUtil(context).getName("dbName", "DB Master")
        tableInfo["tableName"] = PreferenceUtil(context).getName("tableName", "DB Master")

        compositeDisposable.add(tableRepository.getTableInfo(tableInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result->


                }, { error->

                })
        )
    }
}