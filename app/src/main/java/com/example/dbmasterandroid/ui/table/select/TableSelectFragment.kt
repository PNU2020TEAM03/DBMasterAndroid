package com.example.dbmasterandroid.ui.table.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmasterandroid.MainActivityApplication
import com.example.dbmasterandroid.R
import kotlinx.android.synthetic.main.fragment_table_select.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableSelectFragment: Fragment() {

    private val viewModel: TableSelectViewModel by viewModel()

    private val dbName = MainActivityApplication.preferences.getName("dbName", "noName")

    private lateinit var adapter: TableSelectListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel.getAllTableList(dbName)

        adapter = TableSelectListAdapter(viewModel)

        return inflater.inflate(R.layout.fragment_table_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        table_select_list.adapter = adapter
        table_select_list.setHasFixedSize(true)
        table_select_list.layoutManager = LinearLayoutManager(context)

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

        btn_table_create.setOnClickListener {
            findNavController().navigate(R.id.action_tableSelectFragment_to_tableCreateNameFragment)
        }
    }
}