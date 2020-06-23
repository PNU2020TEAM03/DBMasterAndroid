package com.example.dbmasterandroid.ui.main.tabledata

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_table_all_data.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableDataFragment : BaseFragment<TableDataViewModel>() {

    private lateinit var adapter: TableDataAdapter
    private lateinit var searchDataAdapter: TableSearchDataAdapter
    private lateinit var sortedDataAdapter: TableSortedDataAdapter

    override val layoutResourceId: Int
        get() = R.layout.fragment_table_all_data

    override val viewModel: TableDataViewModel by viewModel()

    override fun initView() {
        adapter = TableDataAdapter(viewModel)
        searchDataAdapter = TableSearchDataAdapter(viewModel)
        sortedDataAdapter = TableSortedDataAdapter(viewModel)

        table_data_main_recycler_view.adapter = adapter
        table_data_main_recycler_view.setHasFixedSize(true)
        table_data_main_recycler_view.layoutManager = LinearLayoutManager(context)

        table_data_search_recycler_view.adapter = searchDataAdapter
        table_data_search_recycler_view.setHasFixedSize(true)
        table_data_search_recycler_view.layoutManager = LinearLayoutManager(context)

        table_data_sorted_recycler_view.adapter = sortedDataAdapter
        table_data_sorted_recycler_view.setHasFixedSize(true)
        table_data_sorted_recycler_view.layoutManager = LinearLayoutManager(context)

        viewModel.getAllTableData()
    }

    override fun initData() {
        viewModel.tableSearchComplete.observe(viewLifecycleOwner, Observer {
            table_data_main_recycler_view.visibility = View.GONE
            table_data_search_recycler_view.visibility = View.VISIBLE
            table_data_sorted_recycler_view.visibility = View.GONE
            searchDataAdapter.notifyDataSetChanged()
        })
        viewModel.tableSortComplete.observe(viewLifecycleOwner, Observer {
            table_data_main_recycler_view.visibility = View.GONE
            table_data_search_recycler_view.visibility = View.GONE
            table_data_sorted_recycler_view.visibility = View.VISIBLE
            sortedDataAdapter.notifyDataSetChanged()
        })
        viewModel.networkInvalidLiveData.observe(viewLifecycleOwner, Observer {
            table_all_data_empty_text.text = it
            table_all_data_empty_text.setTextColor(Color.RED)
        })
        viewModel.tableDataListLiveData.observe(viewLifecycleOwner, Observer {
            val columnNames = viewModel.getTableColumnNames()
            for (columnName in columnNames) {
                setColumnNameTextView(columnName)
            }
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
                table_data_main_recycler_view.visibility = View.VISIBLE
                table_data_sorted_recycler_view.visibility = View.GONE
                table_data_search_recycler_view.visibility = View.GONE
                return false
            }
        })
    }

    private fun setColumnNameTextView(columnName: String) {
        val columnNameTextView = TextView(context)
        columnNameTextView.apply {
            var isClicked = 0

            text = columnName
            setTextColor(Color.BLACK)
            textSize = 20.0F
            setPadding(50, 0, 50, 50)

            setOnClickListener {
                isClicked++
                when (isClicked % 3) {
                    1 -> {
                        val direction = "DESC"
                        viewModel.sortedTableData(columnName, direction)
                        Snackbar.make(it, "$columnName 칼럼을 내림차순으로 정렬합니다.", Snackbar.LENGTH_SHORT).show()
                    }
                    2 -> {
                        val direction = "ASC"
                        viewModel.sortedTableData(columnName, direction)
                        Snackbar.make(it, "$columnName 칼럼을 오름차순으로 정렬합니다.", Snackbar.LENGTH_SHORT).show()
                    }
                    0 -> {
                        table_data_main_recycler_view.visibility = View.VISIBLE
                        table_data_search_recycler_view.visibility = View.GONE
                        table_data_sorted_recycler_view.visibility = View.GONE
                        Snackbar.make(it, "원래 상태의 테이블로 되돌립니다.", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
        table_data_main_column_name.addView(columnNameTextView)
    }

}