package com.example.dbmasterandroid.ui.custom

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_custom_query.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CustomQueryFragment: BaseFragment<CustomQueryViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_custom_query
    override val viewModel: CustomQueryViewModel by viewModel()

    private lateinit var adapter: CustomQueryAdapter

    override fun initView() {
        custom_query_input.setSelection(0)

        adapter = CustomQueryAdapter(viewModel)

        custom_query_recycler.adapter = adapter
        custom_query_recycler.setHasFixedSize(true)
        custom_query_recycler.layoutManager = LinearLayoutManager(context)
    }

    override fun initData() {
        viewModel.networkInvalid.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })
        viewModel.queryInvalid.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })
        viewModel.queryComplete.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })
    }

    override fun initFinish() {
        btn_custom_query.setOnClickListener {
            val query = custom_query_input.text.toString()
            viewModel.customQuery(query)
        }
    }

}