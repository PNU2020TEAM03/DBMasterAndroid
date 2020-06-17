package com.example.dbmasterandroid.ui.table.control

import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_table_control.*
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
        /* insert */
        btn_table_control_insert.setOnClickListener {

        }
        /* update */
        btn_table_control_update.setOnClickListener {

        }
        /* delete */
        btn_table_control_delete.setOnClickListener {

        }
        /* join */
        btn_table_control_join.setOnClickListener {

        }
        /* export */
        btn_table_control_export.setOnClickListener {

        }
    }

}