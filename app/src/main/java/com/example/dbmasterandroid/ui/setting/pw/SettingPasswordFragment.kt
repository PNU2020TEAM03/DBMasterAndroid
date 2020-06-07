package com.example.dbmasterandroid.ui.setting.pw

import android.graphics.Color
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_setting_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingPasswordFragment: BaseFragment<SettingPasswordViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_setting_password

    override val viewModel: SettingPasswordViewModel by viewModel()

    override fun initView() {}

    override fun initData() {
        viewModel.passwordInvalid.observe(viewLifecycleOwner, Observer { message ->
            setTextPasswordInvalid(message)
        })

        viewModel.passwordValid.observe(viewLifecycleOwner, Observer {
            /* 비밀번호 변경되어서 로그인 화면으로 가게됨 */
            /* 1. Preference 제거 */
            PreferenceUtil(requireContext()).deletePreference()
            
            /* 2. Fragment Back Stack 모두 제거 */
            /* TODO 테스트 필요. */
            clearBackStack()

            /* 3. Navigate */
            findNavController().navigate(R.id.action_settingPasswordFragment_to_loginFragment)

            /* 4. Notification */
            Snackbar.make(requireView(), "새로운 비밀번호로 다시 로그인 해주세요.", Snackbar.LENGTH_SHORT).show()
        })
    }

    override fun initFinish() {
        btn_setting_password.setOnClickListener {
            val newPassword = setting_new_password_input.text.toString()
            val newPasswordCheck = setting_new_password_input_check.text.toString()
            val oldPassword = setting_old_password_input.text.toString()
            viewModel.checkPasswordValid(newPassword, newPasswordCheck, oldPassword)
        }
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

    private fun setTextPasswordInvalid(message: String) {
        setting_password_valid.text = message
        setting_password_valid.setTextColor(Color.RED)
    }
}