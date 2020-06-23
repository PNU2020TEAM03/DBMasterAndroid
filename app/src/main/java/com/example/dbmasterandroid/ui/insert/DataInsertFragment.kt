package com.example.dbmasterandroid.ui.insert

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.example.dbmasterandroid.data.dto.ColumnInfoDTO
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_data_insert.*
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
    }

    override fun initFinish() {
        btn_data_insert.setOnClickListener {

        }
    }
}