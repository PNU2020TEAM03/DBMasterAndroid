package com.example.dbmasterandroid.ui.update

import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataUpdateFragment: BaseFragment<DataUpdateViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_data_update

    override val viewModel: DataUpdateViewModel by viewModel()

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun initFinish() {
        TODO("Not yet implemented")
    }
}