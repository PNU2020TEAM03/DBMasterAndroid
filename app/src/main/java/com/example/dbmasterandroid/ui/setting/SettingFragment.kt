package com.example.dbmasterandroid.ui.setting

import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.example.dbmasterandroid.utils.PreferenceUtil
import kotlinx.android.synthetic.main.fragment_setting.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment: BaseFragment<SettingViewModel>() {

    override val viewModel: SettingViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_setting

    override fun initView() {
        setting_db_name.text = PreferenceUtil(requireContext()).getName("dbName", "DB Master")
        setting_table_name.text = PreferenceUtil(requireContext()).getName("tableName", "DB Master")
    }

    override fun initData() {
        /**
         *  데이터 Observe 추가 위치
         */
    }

    override fun initFinish() {
        /* 비밀번호 변경 */
        btn_setting_change_pw.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_settingPasswordFragment)
        }

        /* 테이블 이름 변경 */
        btn_setting_change_table_name.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_settingNameFragment)
        }

        /* 로그아웃 */
        btn_setting_logout.setOnClickListener {
            clearPreference()
            clearBackStack()
            findNavController().navigate(R.id.action_settingFragment_to_loginFragment)
        }

        /* 테이블 삭제 */
        btn_setting_table_drop.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_settingDropFragment)
        }
    }

    private fun clearPreference() {
        PreferenceUtil(requireContext()).deletePreference()
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