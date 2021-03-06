package com.example.dbmasterandroid.ui.table.control

import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_table_control.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableControlFragment: BaseFragment<TableControlViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_table_control

    override val viewModel: TableControlViewModel by viewModel()

    override fun initView() {}

    override fun initData() {}

    override fun initFinish() {
        /* Custom */
        btn_table_control_custom.setOnClickListener {
            findNavController().navigate(R.id.action_tableControlFragment_to_customQueryFragment)
        }
        /* insert */
        btn_table_control_insert.setOnClickListener {
            findNavController().navigate(R.id.action_tableControlFragment_to_dataInsertFragment)
        }
        /* join */
        btn_table_control_join.setOnClickListener {
            findNavController().navigate(R.id.action_tableControlFragment_to_tableJoinFragment)
        }
        /* export */
        btn_table_control_export.setOnClickListener {
            findNavController().navigate(R.id.action_tableControlFragment_to_dataExportFragment)
        }
    }

}