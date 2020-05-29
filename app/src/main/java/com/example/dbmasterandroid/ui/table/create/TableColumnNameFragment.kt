package com.example.dbmasterandroid.ui.table.create

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_column_create_name.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TableColumnNameFragment: BaseFragment<TableCreateViewModel>() {

    override val viewModel: TableCreateViewModel by sharedViewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_column_create_name

    override fun initView() {}

    override fun initData() {
        viewModel.columnNameInvalid.observe(viewLifecycleOwner, Observer {
            column_name_valid.text = "사용 할 수 없는 이름입니다."
            column_name_valid.setTextColor(Color.RED)
        })

        viewModel.columnNameValid.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_tableColumnNameFragment_to_tableColumnTypeFragment)
        })
    }

    override fun initFinish() {
        btn_column_name_next.setOnClickListener {
            val columnName = column_name_input.text.toString()
            viewModel.checkColumnNameValid(columnName)
        }
    }
}