package com.example.dbmasterandroid.ui.main.tabledata

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_table_all_data.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableDataFragment : BaseFragment<TableDataViewModel>() {

    private lateinit var adapter: TableDataAdapter
    private lateinit var searchDataAdapter: TableSearchDataAdapter

    override val layoutResourceId: Int
        get() = R.layout.fragment_table_all_data

    override val viewModel: TableDataViewModel by viewModel()

    override fun initView() {
        adapter = TableDataAdapter(viewModel)
        searchDataAdapter = TableSearchDataAdapter(viewModel)

        table_data_main_recycler_view.adapter = adapter
        table_data_main_recycler_view.setHasFixedSize(true)
        table_data_main_recycler_view.layoutManager = LinearLayoutManager(context)

        table_data_search_recycler_view.adapter = searchDataAdapter
        table_data_search_recycler_view.setHasFixedSize(true)
        table_data_search_recycler_view.layoutManager = LinearLayoutManager(context)

        viewModel.getAllTableData()
    }

    override fun initData() {
        viewModel.tableSearchComplete.observe(viewLifecycleOwner, Observer {
            table_data_main_scroll_view.visibility = View.GONE
            table_data_search_scroll_view.visibility = View.VISIBLE
            searchDataAdapter.notifyDataSetChanged()
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
                viewModel.searchTableData(query.toString())
                return false
            }

            /* 현재 타이핑 중인 쿼리 */
            override fun onQueryTextChange(newText: String?): Boolean {
                table_data_main_scroll_view.visibility = View.VISIBLE
                table_data_search_scroll_view.visibility = View.GONE
                return false
            }
        })
    }

}