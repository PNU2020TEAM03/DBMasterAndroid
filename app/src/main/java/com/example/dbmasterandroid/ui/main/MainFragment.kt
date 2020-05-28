package com.example.dbmasterandroid.ui.main

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmasterandroid.MainActivityApplication
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.utils.LoadingIndicator
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.main_drawer_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private lateinit var adapter: MainColumnInfoAdapter

    private val viewModel: MainViewModel by viewModel()
    private var mLoadingIndicator: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        mLoadingIndicator = context?.let { LoadingIndicator(it) }
        adapter = MainColumnInfoAdapter(viewModel)

        viewModel.getAllTableData()

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivityApplication

        mainActivity.setUserTableName(viewModel.getUserName(), viewModel.getTableName())

        main_column_list.adapter = adapter
        main_column_list.setHasFixedSize(true)
        main_column_list.layoutManager = LinearLayoutManager(context)

        viewModel.startLoadingLiveData.observe(viewLifecycleOwner, Observer {
            startLoadingIndicator()
        })

        viewModel.stopLoadingLiveData.observe(viewLifecycleOwner, Observer {
            stopLoadingIndicator()
        })

        viewModel.columnInfoListUpdateLiveData.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        viewModel.tableNameLiveData.observe(viewLifecycleOwner, Observer {
            table_main_title.text = it
        })

        viewModel.tableAllDataLiveData.observe(viewLifecycleOwner, Observer {
            if (it.value.isNotEmpty()) {
                table_data_empty_text.visibility = View.GONE
                val columnNames = it.value[0].keys
                for (columnName in columnNames) {
                    setColumnNameTextView(columnName)
                }
                if (it.value.size < 10) {
                    for (index in it.value.indices) {
                        Log.e("MAIN FRAGMENT", "${it.value[index]}")
                    }
                } else {
                    for (index in 0..9) {
                        Log.e("MAIN FRAGMENT", "${it.value[index]}")
                    }
                }
            } else {
                table_data_empty_text.visibility = View.VISIBLE
            }
        })
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

    private fun stopLoadingIndicator() {
        mLoadingIndicator?.let {
            if (it.isShowing) it.cancel()
        }
    }

    private fun startLoadingIndicator() {
        stopLoadingIndicator()
        activity?.let {
            if (!it.isFinishing) {
                mLoadingIndicator = LoadingIndicator(requireContext())
                mLoadingIndicator?.show()
            }
        }
    }
}