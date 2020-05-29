package com.example.dbmasterandroid.ui.main

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmasterandroid.MainActivityApplication
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.example.dbmasterandroid.utils.LoadingIndicator
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.w3c.dom.Text

class MainFragment : BaseFragment<MainViewModel>() {

    private lateinit var adapter: MainColumnInfoAdapter

    override val layoutResourceId: Int
        get() = R.layout.fragment_main

    override val viewModel: MainViewModel by viewModel()

    override fun initView() {
        adapter = MainColumnInfoAdapter(viewModel)
        viewModel.getAllTableData()

        val mainActivity = activity as MainActivityApplication

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

        viewModel.tableAllDataLiveData.observe(viewLifecycleOwner, Observer {
            if (it.value.isNotEmpty()) {
                table_data_empty_text.visibility = View.GONE
                for (columnName in it.value[0].keys) {
                    setColumnNameTextView(columnName)
                }
                if (it.value.size < 10) {
                    for (index in it.value.indices) {
                        val rowData = it.value[index]
                        setRowDataTextView(rowData, index)
                        Log.e("MAIN FRAGMENT < 10", "${it.value[index]}")
                    }
                } else {
                    for (index in 0..9) {
                        val rowData = it.value[index]
                        setRowDataTextView(rowData, index)
                        Log.e("MAIN FRAGMENT > 10", "${it.value[index]}")
                    }
                }
            } else {
                table_data_empty_text.visibility = View.VISIBLE
            }
        })
    }

    override fun initFinish() {}

    private fun setRowDataTextView(data: HashMap<String, String>, index: Int) {
        when (index) {
            0->setRowColumnDataTextView(table_row0, data)
            1->setRowColumnDataTextView(table_row1, data)
            2->setRowColumnDataTextView(table_row2, data)
            3->setRowColumnDataTextView(table_row3, data)
            4->setRowColumnDataTextView(table_row4, data)
            5->setRowColumnDataTextView(table_row5, data)
            6->setRowColumnDataTextView(table_row6, data)
            7->setRowColumnDataTextView(table_row7, data)
            8->setRowColumnDataTextView(table_row8, data)
            9->setRowColumnDataTextView(table_row9, data)
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