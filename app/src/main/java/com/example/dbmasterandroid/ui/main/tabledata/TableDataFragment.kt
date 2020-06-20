package com.example.dbmasterandroid.ui.main.tabledata

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_table_all_data.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableDataFragment : BaseFragment<TableDataViewModel>() {

    private lateinit var adapter: TableDataAdapter

    override val layoutResourceId: Int
        get() = R.layout.fragment_table_all_data

    override val viewModel: TableDataViewModel by viewModel()

    override fun initView() {
        adapter = TableDataAdapter(viewModel)

        table_data_main_recycler_view.adapter = adapter
        table_data_main_recycler_view.setHasFixedSize(true)
        table_data_main_recycler_view.layoutManager = LinearLayoutManager(context)

        viewModel.getAllTableData()
    }

    override fun initData() {
        viewModel.tableSearchComplete.observe(viewLifecycleOwner, Observer {
        })
        viewModel.networkInvalidLiveData.observe(viewLifecycleOwner, Observer {
            table_all_data_empty_text.text = it
            table_all_data_empty_text.setTextColor(Color.RED)
        })
        viewModel.tableDataListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })
    }

    override fun initFinish() {
        table_data_search_view.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            /* 검색 버튼 눌렀을 때 제출되는 쿼리 */
            override fun onQueryTextSubmit(query: String?): Boolean {
//                viewModel.searchTableData(query.toString())
                Log.e("Search Process Query", query.toString())
                return false
            }

            /* 현재 타이핑 중인 쿼리 */
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

}