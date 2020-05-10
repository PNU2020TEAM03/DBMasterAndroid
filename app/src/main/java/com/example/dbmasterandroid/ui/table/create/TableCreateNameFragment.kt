package com.example.dbmasterandroid.ui.table.create

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import kotlinx.android.synthetic.main.fragment_table_create_name.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableCreateNameFragment: Fragment() {

    private val viewModel: TableCreateViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_table_create_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_table_name.setOnClickListener {
            val tableName = id_input_table_name.text.toString()
            viewModel.checkTableNameValid(tableName)
        }

        viewModel.tableNameValid.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_tableCreateNameFragment_to_tableCreateInfoFragment)
        })

        viewModel.tableNameInvalid.observe(viewLifecycleOwner, Observer {
            table_name_valid.text = "사용할 수 없는 테이블 이름입니다."
            table_name_valid.setTextColor(Color.RED)
        })
    }
}