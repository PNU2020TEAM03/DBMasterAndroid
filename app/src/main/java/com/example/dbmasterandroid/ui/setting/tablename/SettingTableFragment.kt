package com.example.dbmasterandroid.ui.setting.tablename

import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingTableFragment: BaseFragment<SettingTableViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_setting_table_name

    override val viewModel: SettingTableViewModel by viewModel()

    override fun initView() {}

    override fun initData() {}

    override fun initFinish() {}

}