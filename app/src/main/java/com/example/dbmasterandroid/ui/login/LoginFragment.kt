package com.example.dbmasterandroid.ui.login

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: BaseFragment<LoginViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_login

    override val viewModel: LoginViewModel by viewModel()

    private var name: String? = null
    private var pw: String? = null

    override fun initView() {}

    override fun initData() {
        viewModel.networkInvalid.observe(viewLifecycleOwner, Observer {
            connection_valid.text = "네트워크에 문제가 발생했습니다."
            connection_valid.setTextColor(Color.RED)
        })

        viewModel.idInvalid.observe(viewLifecycleOwner, Observer {
            connection_valid.text = "존재하지 않는 아이디 입니다."
            connection_valid.setTextColor(Color.RED)
        })

        viewModel.connectionInvalid.observe(viewLifecycleOwner, Observer {
            connection_valid.text = "해당 아이디와 비밀번호가 일치하지 않습니다."
            connection_valid.setTextColor(Color.RED)
        })

        viewModel.connectionValid.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_loginFragment_to_tableSelectFragment)
        })
    }

    override fun initFinish() {
        btn_signup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpMainFragment)
        }

        btn_login.setOnClickListener {
            name = ed_id.text.toString()
            pw = ed_pw.text.toString()

            if(name != "" && pw != "") {
                viewModel.connect(name!!, pw!!)
            } else {
                connection_valid.text = "아이디나 비밀번호를 입력하지 않았습니다."
                connection_valid.setTextColor(Color.RED)
            }
        }
    }
}