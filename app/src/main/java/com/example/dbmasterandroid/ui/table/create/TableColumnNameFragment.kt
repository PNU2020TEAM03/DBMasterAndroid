package com.example.dbmasterandroid.ui.table.create

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.dbmasterandroid.R
import kotlinx.android.synthetic.main.fragment_column_create_name.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TableColumnNameFragment: Fragment() {

    private val viewModel: TableCreateViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_column_create_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_column_name_next.setOnClickListener {
            val columnName = column_name_input.text.toString()
            viewModel.checkColumnNameValid(columnName)
        }

        viewModel.columnNameInvalid.observe(viewLifecycleOwner, Observer {
            column_name_valid.text = "사용 할 수 없는 이름입니다."
            column_name_valid.setTextColor(Color.RED)
        })

        viewModel.columnNameValid.observe(viewLifecycleOwner, Observer {
            /* TODO 칼럼 데이터 타입 받는 화면으로 넘어가야 함. */
        })
    }
}