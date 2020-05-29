package com.example.dbmasterandroid.ui.signup

import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_signup_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpMainFragment: BaseFragment<SignUpViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_signup_main

    override val viewModel: SignUpViewModel by sharedViewModel()

    override fun initView() {}

    override fun initData() {}

    override fun initFinish() {
        btn_signup_next_main.setOnClickListener {
            findNavController().navigate(R.id.action_signUpMainFragment_to_signUpIdFragment)
        }
    }
}