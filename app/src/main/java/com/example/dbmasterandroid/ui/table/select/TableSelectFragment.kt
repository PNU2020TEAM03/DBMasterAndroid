package com.example.dbmasterandroid.ui.table.select

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmasterandroid.MainActivityApplication
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_table_select.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableSelectFragment: BaseFragment<TableSelectViewModel>() {

    override val viewModel: TableSelectViewModel by viewModel()

    private val dbName = MainActivityApplication.preferences.getName("dbName", "noName")

    private lateinit var adapter: TableSelectListAdapter

    override val layoutResourceId: Int
        get() = R.layout.fragment_table_select

    override fun initView() {
        viewModel.getAllTableList(dbName)

        adapter = TableSelectListAdapter(viewModel)

        table_select_list.adapter = adapter
        table_select_list.setHasFixedSize(true)
        table_select_list.layoutManager = LinearLayoutManager(context)
    }

    override fun initData() {
        viewModel.tableListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        viewModel.tableListSizeLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                0 -> {
                    table_select_empty.visibility = View.VISIBLE
                    table_select_list.visibility = View.GONE
                }
                else -> {
                    table_select_list.visibility = View.VISIBLE
                    table_select_empty.visibility = View.GONE
                }
            }
        })
    }

    override fun initFinish() {
        btn_table_create.setOnClickListener {
            if (viewModel.getTableSize() >= 10) {
                Snackbar.make(it, "테이블의 갯수가 10개입니다. 더 이상 생성할 수 없습니다.", Snackbar.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_tableSelectFragment_to_tableCreateNameFragment)
            }
        }
    }
}