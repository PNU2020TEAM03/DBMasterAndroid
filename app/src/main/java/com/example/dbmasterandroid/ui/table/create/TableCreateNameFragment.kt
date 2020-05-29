package com.example.dbmasterandroid.ui.table.create

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_table_create_name.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TableCreateNameFragment: BaseFragment<TableCreateViewModel>() {

    override val viewModel: TableCreateViewModel by sharedViewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_table_create_name

    override fun initView() {}

    override fun initData() {
        viewModel.tableNameValid.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_tableCreateNameFragment_to_tableCreateInfoFragment)
        })

        viewModel.tableNameInvalid.observe(viewLifecycleOwner, Observer {
            table_name_valid.text = it
            table_name_valid.setTextColor(Color.RED)
        })
    }

    override fun initFinish() {
        btn_table_name.setOnClickListener {
            val tableName = id_input_table_name.text.toString()
            viewModel.checkTableNameValid(tableName)
        }
    }
}