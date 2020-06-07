package com.example.dbmasterandroid.ui.setting

import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.example.dbmasterandroid.utils.PreferenceUtil
import kotlinx.android.synthetic.main.fragment_setting.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment: BaseFragment<SettingViewModel>() {

    override val viewModel: SettingViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_setting

    override fun initView() {
        setting_db_name.text = PreferenceUtil(requireContext()).getName("dbName", "DB Master")
        setting_table_name.text = PreferenceUtil(requireContext()).getName("tableName", "DB Master")
    }

    override fun initData() {}

    override fun initFinish() {
        btn_setting_change_pw.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_settingPasswordFragment)
        }
    }
}