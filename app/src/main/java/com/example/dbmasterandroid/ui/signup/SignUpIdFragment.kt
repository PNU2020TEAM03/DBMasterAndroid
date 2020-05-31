package com.example.dbmasterandroid.ui.signup

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_signup_id.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpIdFragment : BaseFragment<SignUpViewModel>() {

    private var name: String? = null

    override val viewModel: SignUpViewModel by sharedViewModel()
    override val layoutResourceId: Int
        get() = R.layout.fragment_signup_id

    override fun initView() {}

    override fun initData() {
        viewModel.nameValid.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_signUpIdFragment_to_signUpPwFragment)
        })

        viewModel.nameInvalid.observe(viewLifecycleOwner, Observer {
            id_valid.text = "사용할 수 없는 이름입니다."
            id_valid.setTextColor(Color.RED)
        })

        viewModel.networkInvalid.observe(viewLifecycleOwner, Observer {
            id_valid.text = "네트워크 또는 서버문제가 발생했습니다."
            id_valid.setTextColor(Color.RED)
        })
    }

    override fun initFinish() {
        btn_signup_next_id.setOnClickListener {
            name = id_input.text.toString()
            viewModel.checkNameValid(name)
        }
    }
}