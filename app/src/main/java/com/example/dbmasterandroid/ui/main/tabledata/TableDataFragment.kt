package com.example.dbmasterandroid.ui.main.tabledata

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
        viewModel.tableDataListLiveData.observe(viewLifecycleOwner, Observer {
            val tableDataListSize = viewModel.getTableListSize()
            val tableColumnNames = viewModel.getTableColumnNames()

            for (columnName in tableColumnNames) {
                setColumnNameTextView(columnName)
            }

            for (position in 0 until tableDataListSize) {
                val tableDataItem = viewModel.getTableListItem(position)
                setRowColumnDataTextView(tableDataItem)
            }
        })
    }

    override fun initFinish() {
        table_data_search_view.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setRowColumnDataTextView(data: HashMap<String, String>) {
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
        table_data_main.addView(tableRow)
        rowDataList.clear()
    }

    private fun setColumnNameTextView(columnName: String) {
        val columnNameTextView = TextView(context)
        columnNameTextView.apply {
            text = columnName
            setTextColor(resources.getColor(R.color.black))
            textSize = 20.0F
            setPadding(50, 0, 50, 50)
        }
        table_data_main_column.addView(columnNameTextView)
    }
}