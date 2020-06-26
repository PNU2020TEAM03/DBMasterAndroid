package com.example.dbmasterandroid.ui.custom

import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CustomQueryFragment: BaseFragment<CustomQueryViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_custom_query
    override val viewModel: CustomQueryViewModel by viewModel()

    override fun initView() {}

    override fun initData() {}

    override fun initFinish() {}

}