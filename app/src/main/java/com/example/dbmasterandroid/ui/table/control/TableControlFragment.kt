package com.example.dbmasterandroid.ui.table.control

import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableControlFragment: BaseFragment<TableControlViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_table_control

    override val viewModel: TableControlViewModel by viewModel()

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