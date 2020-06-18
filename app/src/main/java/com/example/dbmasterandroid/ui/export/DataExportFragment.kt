package com.example.dbmasterandroid.ui.export

import android.graphics.Color
import androidx.lifecycle.Observer
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_data_export.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataExportFragment: BaseFragment<DataExportViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_data_export

    override val viewModel: DataExportViewModel by viewModel()

    override fun initView() {}

    override fun initData() {
        /* 오류 발생 또는 이메일 형식 Invalid */
        viewModel.exportInvalid.observe(viewLifecycleOwner, Observer { message->
            setExportResultTextView(message, false)
        })
        /* export 완료되었을 때. */
        viewModel.exportClear.observe(viewLifecycleOwner, Observer { message->
            setExportResultTextView(message, true)
        })
    }

    override fun initFinish() {
        btn_data_export.setOnClickListener {
            val emailAddress = data_export_email_input.text.toString()
            viewModel.exportTable(emailAddress)
        }
    }

    private fun setExportResultTextView(message: String, isValid: Boolean) {
        when (isValid) {
            true->{
                data_export_email_valid.text = message
                data_export_email_valid.setTextColor(Color.BLUE)
            }
            false->{
                data_export_email_valid.text = message
                data_export_email_valid.setTextColor(Color.RED)
            }
        }
    }
}