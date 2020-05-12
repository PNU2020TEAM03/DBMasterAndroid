package com.example.dbmasterandroid.ui.table.create

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.dbmasterandroid.R
import kotlinx.android.synthetic.main.fragment_column_create_type.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TableColumnTypeFragment: Fragment() {

    private val viewModel: TableCreateViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_column_create_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        current_column_name.text = "현재 칼럼 이름: ${viewModel.currentColumnName}"

        column_data_types_spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position)
                column_data_types_text1.text = item.toString()

                when (position) {
                    0->{
                        column_data_types_spinner2.adapter = ArrayAdapter(context!!,
                                R.layout.support_simple_spinner_dropdown_item,
                                resources.getStringArray(R.array.column_numeric_types))
                    }
                    1->{
                        column_data_types_spinner2.adapter = ArrayAdapter(context!!,
                                R.layout.support_simple_spinner_dropdown_item,
                                resources.getStringArray(R.array.column_datetime_types))
                    }
                    2->{
                        column_data_types_spinner2.adapter = ArrayAdapter(context!!,
                                R.layout.support_simple_spinner_dropdown_item,
                                resources.getStringArray(R.array.column_charStr_types))
                    }
                    3->{
                        column_data_types_spinner2.adapter = ArrayAdapter(context!!,
                                R.layout.support_simple_spinner_dropdown_item,
                                resources.getStringArray(R.array.column_uCharStr_types))
                    }
                    4->{
                        column_data_types_spinner2.adapter = ArrayAdapter(context!!,
                                R.layout.support_simple_spinner_dropdown_item,
                                resources.getStringArray(R.array.column_binary_types))
                    }
                    5->{
                        column_data_types_spinner2.adapter = ArrayAdapter(context!!,
                                R.layout.support_simple_spinner_dropdown_item,
                                resources.getStringArray(R.array.column_miscellaneous_types))
                    }
                }
            }
        }

        column_data_types_spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position)
                column_data_types_text2.text = item.toString()
            }
        }

        column_key_spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0->{
                        column_key_spinner2.visibility = View.GONE
                    }
                    1->{
                        column_key_spinner2.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}