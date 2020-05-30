package com.example.dbmasterandroid.ui.main

import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainTableDataFragment : BaseFragment<MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_table_all_data

    override val viewModel: MainViewModel by sharedViewModel()

    override fun initView() {}

    override fun initData() {}

    override fun initFinish() {}

}