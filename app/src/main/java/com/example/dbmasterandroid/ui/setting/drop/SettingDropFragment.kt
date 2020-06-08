package com.example.dbmasterandroid.ui.setting.drop

import android.graphics.Color
import androidx.lifecycle.Observer
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_setting_table_drop.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingDropFragment: BaseFragment<SettingDropViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_setting_table_drop

    override val viewModel: SettingDropViewModel by viewModel()

    override fun initView() {}

    override fun initData() {
        /* 테이블 이름 일치할 때. */
        viewModel.tableDropValid.observe(viewLifecycleOwner, Observer {

        })
        /* 테이블 이름 일치하지 않을 때. */
        viewModel.tableDropInvalid.observe(viewLifecycleOwner, Observer { message->
            setTextDropInvalid(message)
        })
    }

    override fun initFinish() {
        btn_setting_table_drop.setOnClickListener {
            val tableName = setting_table_drop_input.text.toString()
            viewModel.checkTableNameDropValid(tableName)
        }
    }

    private fun setTextDropInvalid(message: String) {
        setting_table_drop_valid.text = message
        setting_table_drop_valid.setTextColor(Color.RED)
    }

}