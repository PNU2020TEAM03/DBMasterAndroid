package com.example.dbmasterandroid.ui.setting.tablename

import android.graphics.Color
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_setting_table_name.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingNameFragment: BaseFragment<SettingNameViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_setting_table_name

    override val viewModel: SettingNameViewModel by viewModel()

    override fun initView() {}

    override fun initData() {
        /* 테이블 이름 변경 성공했을 때. */
        viewModel.tableNameSetValid.observe(viewLifecycleOwner, Observer {
            /* 1. Fragment Back Stack 모두 제거 */
            clearBackStack()
            /* 2. Navigate */
            findNavController().navigate(R.id.action_settingNameFragment_to_tableSelectFragment)
            /* 3. Notification */
            Snackbar.make(requireView(), "테이블 이름이 성공적으로 변경되었습니다.", Snackbar.LENGTH_SHORT).show()
        })

        /* 테이블 이름 변경 실패했을 때. */
        viewModel.tableNameSetInvalid.observe(viewLifecycleOwner, Observer { message->
            setTextTableNameInvalid(message)
        })
    }

    override fun initFinish() {
        btn_setting_table_name.setOnClickListener {
            val tableName = setting_table_name_input.text.toString()
            viewModel.renameTable(tableName)
        }
    }

    private fun setTextTableNameInvalid(message: String) {
        setting_table_name_valid.text = message
        setting_table_name_valid.setTextColor(Color.RED)
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