package com.example.dbmasterandroid.ui.main

import android.util.Log
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmasterandroid.MainActivity
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<MainViewModel>() {

    private lateinit var adapter: MainColumnInfoAdapter

    override val layoutResourceId: Int
        get() = R.layout.fragment_main

    override val viewModel: MainViewModel by viewModel()

    override fun initView() {
        adapter = MainColumnInfoAdapter(viewModel)
        viewModel.getAllTableData()

        val mainActivity = activity as MainActivity

        mainActivity.setUserTableName(viewModel.getUserName(), viewModel.getTableName())

        main_column_list.adapter = adapter
        main_column_list.setHasFixedSize(true)
        main_column_list.layoutManager = LinearLayoutManager(context)
    }

    override fun initData() {
        viewModel.columnInfoListUpdateLiveData.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        viewModel.tableNameLiveData.observe(viewLifecycleOwner, Observer {
            table_main_title.text = it
        })

        viewModel.tableAllDataListUpdateLiveData.observe(viewLifecycleOwner, Observer {
            val tableAllDataListSize = viewModel.getTableDataListSize()
            if (tableAllDataListSize != 0) {
                table_data_empty_text.visibility = View.GONE

                for (columnName in viewModel.getTableColumnNames()) {
                    setColumnNameTextView(columnName)
                }

                /* 테이블에 데이터가 10개 미만일때. */
                if (tableAllDataListSize < 10) {
                    for (index in 0 until tableAllDataListSize) {
                        val tableAllDataListItem = viewModel.getTableDataListItem(index)
                        setRowDataTextView(tableAllDataListItem, index)
                    }
                }
                /* 테이블에 데이터가 10개 이상일때. */
                else {
                    for (index in 0..9) {
                        val tableAllDataListItem = viewModel.getTableDataListItem(index)
                        setRowDataTextView(tableAllDataListItem, index)
                    }
                }
            } else {
                table_data_empty_text.visibility = View.VISIBLE
            }
        })
    }

    override fun initFinish() {
        btn_table_data_all.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_tableDataFragment)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.clearList()
        Log.e("ON PAUSE", "ON PAUSE")
    }

    private fun setRowDataTextView(data: HashMap<String, String>, index: Int) {
        when (index) {
            0 -> setRowColumnDataTextView(table_row0, data)
            1 -> setRowColumnDataTextView(table_row1, data)
            2 -> setRowColumnDataTextView(table_row2, data)
            3 -> setRowColumnDataTextView(table_row3, data)
            4 -> setRowColumnDataTextView(table_row4, data)
            5 -> setRowColumnDataTextView(table_row5, data)
            6 -> setRowColumnDataTextView(table_row6, data)
            7 -> setRowColumnDataTextView(table_row7, data)
            8 -> setRowColumnDataTextView(table_row8, data)
            9 -> setRowColumnDataTextView(table_row9, data)
        }
    }

    private fun setRowColumnDataTextView(tableRow: TableRow, data: HashMap<String, String>) {
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
        table_column_name.addView(columnNameTextView)
    }
}