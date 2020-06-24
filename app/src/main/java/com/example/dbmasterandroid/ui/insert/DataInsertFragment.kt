package com.example.dbmasterandroid.ui.insert

import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_data_insert.*
import kotlinx.android.synthetic.main.item_table_insert_list.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataInsertFragment: BaseFragment<DataInsertViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_data_insert

    override val viewModel: DataInsertViewModel by viewModel()

    private lateinit var adapter: DataInsertAdapter

    override fun initView() {
        viewModel.getTableInfo()

        adapter = DataInsertAdapter(viewModel)

        recycler_insert.adapter = adapter
        recycler_insert.setHasFixedSize(true)
        recycler_insert.layoutManager = LinearLayoutManager(context)
    }

    override fun initData() {
        viewModel.networkInvalid.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })
        viewModel.tableInfoValid.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })
        viewModel.insertValid.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })
        viewModel.insertInvalid.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })
    }

    override fun initFinish() {
        btn_data_insert.setOnClickListener {
            val dataList = ArrayList<String>()
            for (index in adapter.editTextList.indices) {
                val editText = adapter.editTextList[index]
                dataList.add(editText.text.toString())
            }
            Log.d("DATA INSERT", "$dataList")
            viewModel.insertData(dataList)
        }
    }
}