package com.example.dbmasterandroid.ui.table.create

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import kotlinx.android.synthetic.main.fragment_column_create_type.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TableColumnTypeFragment: Fragment() {

    private val viewModel: TableCreateViewModel by sharedViewModel()

    private var dataTypeSize: String = ""
    private var dataType: String = ""
    private var dataKey: String = ""

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
                        column_data_types_size_input.isEnabled = false
                        column_data_types_size_valid.text = "사이즈를 입력할 수 없는 데이터 타입입니다."
                        column_data_types_size_valid.setTextColor(Color.RED)
                    }
                    1->{
                        column_data_types_spinner2.adapter = ArrayAdapter(context!!,
                                R.layout.support_simple_spinner_dropdown_item,
                                resources.getStringArray(R.array.column_datetime_types))
                        column_data_types_size_valid.text = "사이즈를 입력할 수 없는 데이터 타입입니다."
                        column_data_types_size_valid.setTextColor(Color.RED)
                    }
                    2->{
                        column_data_types_spinner2.adapter = ArrayAdapter(context!!,
                                R.layout.support_simple_spinner_dropdown_item,
                                resources.getStringArray(R.array.column_charStr_types))
                        column_data_types_size_input.isEnabled = true
                        column_data_types_size_valid.text = "사이즈를 입력해야 하는 데이터 타입입니다."
                        column_data_types_size_valid.setTextColor(Color.GREEN)
                    }
                    3->{
                        column_data_types_spinner2.adapter = ArrayAdapter(context!!,
                                R.layout.support_simple_spinner_dropdown_item,
                                resources.getStringArray(R.array.column_miscellaneous_types))
                        column_data_types_size_valid.text = "사이즈를 입력할 수 없는 데이터 타입입니다."
                        column_data_types_size_valid.setTextColor(Color.RED)
                    }
                }
            }
        }

        column_data_types_spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position).toString()
                column_data_types_text2.text = item
                dataType = item
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

        column_key_spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0->{
                        dataKey = "PK"
                    }
                    1->{
                        dataKey = "FK"
                    }
                }
            }
        }

        btn_column_type_next.setOnClickListener {
            dataTypeSize = column_data_types_size_input.text.toString()
            if (dataTypeSize != "") {
                viewModel.checkColumnCreateValid(dataType, dataKey, dataTypeSize.toInt())
            } else {
                viewModel.checkColumnCreateValid(dataType, dataKey,0)
            }
        }

        viewModel.dataTypeSizeInvalid.observe(viewLifecycleOwner, Observer {
            column_data_types_size_valid.text = "데이터 사이즈가 유효범위를 벗어났습니다."
            column_data_types_size_valid.setTextColor(Color.RED)
        })

        viewModel.dataTypeSizeValid.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_tableColumnTypeFragment_to_tableCreateInfoFragment)
        })
    }
}