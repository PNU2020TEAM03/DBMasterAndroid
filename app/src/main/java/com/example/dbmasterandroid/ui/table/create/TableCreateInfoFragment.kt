package com.example.dbmasterandroid.ui.table.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmasterandroid.R
import kotlinx.android.synthetic.main.fragment_table_create_info.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TableCreateInfoFragment: Fragment() {

    private val viewModel: TableCreateViewModel by sharedViewModel()

    private lateinit var adapter: TableCreateListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        adapter = TableCreateListAdapter(viewModel)

        return inflater.inflate(R.layout.fragment_table_create_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        table_create_info_name.text = "현재 테이블: ${viewModel.currentTableName}"

        table_create_info_list.adapter = adapter
        table_create_info_list.setHasFixedSize(true)
        table_create_info_list.layoutManager = LinearLayoutManager(context)

        btn_table_info_add.setOnClickListener {
            findNavController().navigate(R.id.action_tableCreateInfoFragment_to_tableColumnNameFragment)
        }

        btn_table_create.setOnClickListener {
            /* TODO viewmodel에 가지고 있는 리스트 가지고 테이블 생성하고 테이블 선택화면으로 넘어가야함. */
        }

        viewModel.listUpdateLiveData.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })
    }
}