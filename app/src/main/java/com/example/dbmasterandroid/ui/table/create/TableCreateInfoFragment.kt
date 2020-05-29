package com.example.dbmasterandroid.ui.table.create

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_table_create_info.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TableCreateInfoFragment: BaseFragment<TableCreateViewModel>() {

    override val viewModel: TableCreateViewModel by sharedViewModel()

    private lateinit var adapter: TableCreateListAdapter

    override val layoutResourceId: Int
        get() = R.layout.fragment_table_create_info

    override fun initView() {
        adapter = TableCreateListAdapter(viewModel)

        table_create_info_name.text = "현재 테이블: ${viewModel.currentTableName}"

        table_create_info_list.adapter = adapter
        table_create_info_list.setHasFixedSize(true)
        table_create_info_list.layoutManager = LinearLayoutManager(context)
    }

    override fun initData() {
        viewModel.listUpdateLiveData.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        viewModel.tableCreateValid.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_tableCreateInfoFragment_to_tableSelectFragment)
        })

        viewModel.tableCreateInvalid.observe(viewLifecycleOwner, Observer {
            Snackbar.make(this.requireView(), "테이블을 생성하지 못하였습니다.", Snackbar.LENGTH_SHORT).show()
        })
    }

    override fun initFinish() {
        btn_table_info_add.setOnClickListener {
            findNavController().navigate(R.id.action_tableCreateInfoFragment_to_tableColumnNameFragment)
        }

        btn_table_create.setOnClickListener {
            viewModel.createTable()
        }
    }
}