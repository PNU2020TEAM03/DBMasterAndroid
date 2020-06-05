package com.example.dbmasterandroid.ui.signup

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_signup_pw.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpPwFragment : BaseFragment<SignUpViewModel>() {

    private var password: String? = null

    override val viewModel: SignUpViewModel by sharedViewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_signup_pw

    override fun initView() {}

    override fun initData() {
        viewModel.passwordValid.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_signUpPwFragment_to_signUpFragment)
        })

        viewModel.passwordInvalid.observe(viewLifecycleOwner, Observer {
            pw_valid.text = "사용할 수 없는 비밀번호입니다."
            pw_valid.setTextColor(Color.RED)
        })
    }

    override fun initFinish() {
        btn_signup_next_pw.setOnClickListener {
            password = pw_input.text.toString()

            viewModel.checkPasswordValid(password!!)
        }
    }

}