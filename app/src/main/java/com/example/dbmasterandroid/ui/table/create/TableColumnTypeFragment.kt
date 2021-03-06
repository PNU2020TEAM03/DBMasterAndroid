package com.example.dbmasterandroid.ui.table.create

import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_column_create_type.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TableColumnTypeFragment: BaseFragment<TableCreateViewModel>() {

    override val viewModel: TableCreateViewModel by sharedViewModel()

    private var dataTypeSize: String = ""
    private var dataType: String = ""
    private var dataKey: String = ""

    override val layoutResourceId: Int
        get() = R.layout.fragment_column_create_type

    override fun initView() {
        current_column_name.text = "현재 칼럼 이름: ${viewModel.currentColumnName}"
    }

    override fun initData() {
        viewModel.columnListSizeInvalid.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), "칼럼 갯수가 10개입니다. 더 이상 칼럼을 추가할 수 없습니다.", Snackbar.LENGTH_SHORT).show()
        })

        viewModel.dataTypeSizeInvalid.observe(viewLifecycleOwner, Observer {
            column_data_types_size_valid.text = "데이터 사이즈가 유효범위를 벗어났습니다."
            column_data_types_size_valid.setTextColor(Color.RED)
        })

        viewModel.listUpdateInvalidLiveData.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), "Primary Key 가 이미 존재합니다. 다시 한 번 확인해주세요.", Snackbar.LENGTH_SHORT).show()
        })

        viewModel.listUpdateValidLiveData.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_tableColumnTypeFragment_pop)
        })
    }

    override fun initFinish() {
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

                if (item == "TEXT") {
                    column_data_types_size_input.isEnabled = false
                    column_data_types_size_valid.text = "사이즈를 입력할 수 없는 데이터 타입입니다."
                    column_data_types_size_valid.setTextColor(Color.RED)
                }

                dataType = item
            }
        }

        column_key_spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0->{
                        column_key_spinner2.visibility = View.GONE
                        dataKey = ""
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
                }
            }
        }

        btn_column_type_next.setOnClickListener {
            dataTypeSize = column_data_types_size_input.text.toString()
            if (dataTypeSize != "") {
                try{
                    viewModel.checkColumnCreateValid(dataType, dataKey, dataTypeSize.toInt())
                } catch (e: NumberFormatException) {
                    column_data_types_size_valid.text = "데이터 사이즈가 유효범위를 벗어났습니다."
                    column_data_types_size_valid.setTextColor(Color.RED)
                }
            } else {
                viewModel.checkColumnCreateValid(dataType, dataKey,0)
            }
        }
    }
}