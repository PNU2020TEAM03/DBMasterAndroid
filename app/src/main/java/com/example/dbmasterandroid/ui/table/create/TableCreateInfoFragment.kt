package com.example.dbmasterandroid.ui.table.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dbmasterandroid.R
import kotlinx.android.synthetic.main.fragment_table_create_info.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TableCreateInfoFragment: Fragment() {

    private val viewModel: TableCreateViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_table_create_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        table_create_info_name.text = "현재 테이블: ${viewModel.currentTableName}"
    }
}