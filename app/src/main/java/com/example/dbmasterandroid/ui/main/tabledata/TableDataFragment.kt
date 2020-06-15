package com.example.dbmasterandroid.ui.main.tabledata

import android.util.Log
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_table_all_data.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableDataFragment : BaseFragment<TableDataViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_table_all_data

    override val viewModel: TableDataViewModel by viewModel()

    override fun initView() {
        viewModel.getAllTableData()
    }

    override fun initData() {
        viewModel.tableSearchComplete.observe(viewLifecycleOwner, Observer {
            val searchTableDataListSize = viewModel.getSearchTableListSize()
            val tableColumnNames = viewModel.getTableColumnNames()

            if (searchTableDataListSize == 0) {
                table_all_data_empty_text.text = "검색된 데이터가 없습니다."
                table_all_data_empty_text.visibility = View.VISIBLE
                table_data_main_scroll_view.visibility = View.INVISIBLE
                table_data_search_scroll_view.visibility = View.INVISIBLE
            } else {
                table_all_data_empty_text.visibility = View.INVISIBLE
                table_data_main_scroll_view.visibility = View.INVISIBLE
                table_data_search_scroll_view.visibility = View.VISIBLE

                for (columnName in tableColumnNames) {
                    setColumnNameTextView(columnName, "search")
                }

                for (position in 0 until searchTableDataListSize) {
                    val tableDataItem = viewModel.getSearchTableListItem(position)
                    setRowColumnDataTextView(tableDataItem, "search")
                }
            }
        })
        viewModel.networkInvalidLiveData.observe(viewLifecycleOwner, Observer {
            table_all_data_empty_text.visibility = View.VISIBLE
            table_all_data_empty_text.text = it
            table_data_main_scroll_view.visibility = View.INVISIBLE
            table_data_search_scroll_view.visibility = View.INVISIBLE
        })
        viewModel.tableDataListLiveData.observe(viewLifecycleOwner, Observer {
            val tableDataListSize = viewModel.getTableListSize()
            val tableColumnNames = viewModel.getTableColumnNames()

            if (tableDataListSize == 0) {
                table_all_data_empty_text.visibility = View.VISIBLE
                table_data_main_scroll_view.visibility = View.INVISIBLE
                table_data_search_scroll_view.visibility = View.INVISIBLE
            } else {
                table_all_data_empty_text.visibility = View.INVISIBLE
                table_data_main_scroll_view.visibility = View.VISIBLE
                table_data_search_scroll_view.visibility = View.INVISIBLE

                for (columnName in tableColumnNames) {
                    setColumnNameTextView(columnName, "main")
                }

                for (position in 0 until tableDataListSize) {
                    val tableDataItem = viewModel.getTableListItem(position)
                    setRowColumnDataTextView(tableDataItem, "main")
                }
            }
        })
    }

    override fun initFinish() {
        table_data_search_view.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            /* 검색 버튼 눌렀을 때 제출되는 쿼리 */
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchTableData(query.toString())
                Log.e("Search Process Query", query.toString())
                return false
            }

            /* 현재 타이핑 중인 쿼리 */
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setRowColumnDataTextView(data: HashMap<String, String>, flag: String) {
        val tableRow = TableRow(context)
        val rowDataList = ArrayList<String>()
        for (columnName in data.keys) {
            val columnData = data[columnName]
            rowDataList.add(columnData!!)
        }
        for (rowData in rowDataList) {
            val textView = TextView(context)
            textView.apply {
                text = rowData
                setTextColor(resources.getColor(R.color.black))
                textSize = 20.0F
                setPadding(50, 0, 50, 50)
            }
            tableRow.addView(textView)
        }

        when (flag) {
            "main"->{
                table_data_main.addView(tableRow)
                rowDataList.clear()
            }
            "search"->{
                table_data_search.addView(tableRow)
                rowDataList.clear()
            }
        }
    }

    private fun setColumnNameTextView(columnName: String, flag: String) {
        val columnNameTextView = TextView(context)
        columnNameTextView.apply {
            text = columnName
            setTextColor(resources.getColor(R.color.black))
            textSize = 20.0F
            setPadding(50, 0, 50, 50)
        }
        when (flag) {
            "main"->table_data_main_column.addView(columnNameTextView)
            "search"->table_data_search_column.addView(columnNameTextView)
        }
    }

}