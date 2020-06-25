package com.example.dbmasterandroid.ui.update

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_data_update.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataUpdateFragment: BaseFragment<DataUpdateViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_data_update

    override val viewModel: DataUpdateViewModel by viewModel()

    private val columnNameList = ArrayList<String>()
    private var currentColumnName = ""

    private var pkInfo = HashMap<String, String>()

    override fun initView() {
        val rowData = arguments?.getSerializable("rowData") as HashMap<String, String>
        pkInfo = arguments?.getSerializable("pkInfo") as HashMap<String, String>

        columnNameList.addAll(rowData.keys)

    }

    override fun initData() {
        viewModel.networkInvalid.observe(viewLifecycleOwner, Observer {
            data_update_notify.text = it
            data_update_notify.setTextColor(Color.RED)
        })
        viewModel.updateInvalid.observe(viewLifecycleOwner, Observer {
            data_update_notify.text = it
            data_update_notify.setTextColor(Color.RED)
        })
        viewModel.updateValid.observe(viewLifecycleOwner, Observer {
            val fragmentManager: FragmentManager = parentFragmentManager

            Snackbar.make(requireView(), "업데이트에 성공하였습니다.", Snackbar.LENGTH_SHORT).show()

            fragmentManager.popBackStack()
        })
    }

    override fun initFinish() {
        data_update_column_spinner.adapter = ArrayAdapter(requireContext(), R.layout.item_update_spinner, columnNameList)
        data_update_column_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentColumnName = parent?.getItemAtPosition(position).toString()

                Log.e("UPDATE", currentColumnName)
            }
        }
        btn_data_update.setOnClickListener {
            val dataInfo = HashMap<String, String>()
            dataInfo["columnName"] = currentColumnName
            dataInfo["value"] = data_update_input.text.toString()

            viewModel.updateTableData(pkInfo, dataInfo)
        }
    }
}