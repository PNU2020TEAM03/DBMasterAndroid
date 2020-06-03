package com.example.dbmasterandroid.ui.main.tabledata

import androidx.lifecycle.Observer
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
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

}