package com.example.dbmasterandroid.ui.insert

import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataInsertFragment: BaseFragment<DataInsertViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_data_insert

    override val viewModel: DataInsertViewModel by viewModel()

    override fun initView() {}

    override fun initData() {}

    override fun initFinish() {}
}