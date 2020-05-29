package com.example.dbmasterandroid.ui.setting

import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment: BaseFragment<SettingViewModel>() {

    override val viewModel: SettingViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_setting

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