package com.example.dbmasterandroid.ui.setting.drop

import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_setting_table_drop.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingDropFragment: BaseFragment<SettingDropViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_setting_table_drop

    override val viewModel: SettingDropViewModel by viewModel()

    override fun initView() {}

    override fun initData() {
        /* 테이블 이름 일치할 때. */
        viewModel.tableDropValid.observe(viewLifecycleOwner, Observer {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("테이블 삭제")
            builder.setMessage("테이블을 정말 삭제하시겠습니까?")
            builder.setCancelable(false)
            builder.setPositiveButton("삭제") { _, _ ->
                viewModel.tableDrop()
            }
            builder.setNegativeButton("취소") { _, _ -> }
            builder.show()
        })
        /* 테이블 이름 일치하지 않을 때. */
        viewModel.tableDropInvalid.observe(viewLifecycleOwner, Observer { message->
            setTextDropInvalid(message)
        })
        /* 테이블 삭제 성공했을 때 */
        viewModel.tableDropComplete.observe(viewLifecycleOwner, Observer {
            clearBackStack()
            findNavController().navigate(R.id.action_settingDropFragment_to_tableSelectFragment)
        })
    }

    override fun initFinish() {
        btn_setting_table_drop.setOnClickListener {
            val tableName = setting_table_drop_input.text.toString()
            viewModel.checkTableNameDropValid(tableName)
        }
    }

    private fun setTextDropInvalid(message: String) {
        setting_table_drop_valid.text = message
        setting_table_drop_valid.setTextColor(Color.RED)
    }

    private fun clearBackStack() {
        val fragmentManager: FragmentManager = parentFragmentManager
        val backStackEntryCount = fragmentManager.backStackEntryCount

        if (backStackEntryCount > 0) {
            for (entry in 0 until backStackEntryCount) {
                fragmentManager.popBackStackImmediate()
            }
        }
    }
}