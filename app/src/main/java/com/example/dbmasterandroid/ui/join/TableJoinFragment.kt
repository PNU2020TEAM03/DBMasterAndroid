package com.example.dbmasterandroid.ui.join

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_table_join.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableJoinFragment: BaseFragment<TableJoinViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_table_join
    override val viewModel: TableJoinViewModel by viewModel()

    private lateinit var adapter: TableJoinAdapter

    private var currentTableName = ""
    private var currentColumnName = ""

    override fun initView() {
        viewModel.getAllTableName()

        adapter = TableJoinAdapter(viewModel)

        table_join_recycler.adapter = adapter
        table_join_recycler.setHasFixedSize(true)
        table_join_recycler.layoutManager = LinearLayoutManager(context)
    }

    override fun initData() {
        viewModel.tableNameList.observe(viewLifecycleOwner, Observer {
            table_join_table_spinner.adapter = ArrayAdapter(requireContext(), R.layout.item_update_spinner, it)
        })
        viewModel.columnNameList.observe(viewLifecycleOwner, Observer {
            table_join_column_title.visibility = View.VISIBLE
            table_join_column_spinner.visibility = View.VISIBLE
            table_join_column_spinner.adapter = ArrayAdapter(requireContext(), R.layout.item_update_spinner, it)
        })
        viewModel.tableJoinComplete.observe(viewLifecycleOwner, Observer {
            table_join_recycler.visibility = View.VISIBLE
            adapter.notifyDataSetChanged()
        })
        viewModel.tableJoinInvalid.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })
    }

    override fun initFinish() {
        table_join_table_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentTableName = parent?.getItemAtPosition(position).toString()
                viewModel.getTableInfo(currentTableName)
            }
        }
        table_join_column_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentColumnName = parent?.getItemAtPosition(position).toString()

                Log.e("JOIN", "$currentTableName / $currentColumnName")
            }

        }
        btn_table_join.setOnClickListener {
            viewModel.tableJoin(currentTableName, currentColumnName)
        }
    }

}