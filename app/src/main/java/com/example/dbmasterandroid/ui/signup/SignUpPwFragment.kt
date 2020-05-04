package com.example.dbmasterandroid.ui.signup

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import kotlinx.android.synthetic.main.fragment_signup_pw.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpPwFragment : Fragment() {

    private val viewModel: SignUpViewModel by sharedViewModel()

    private var password: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_signup_pw, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_signup_next_pw.setOnClickListener {
            password = pw_input.text.toString()

            viewModel.checkPasswordValid(password!!)
        }

        viewModel.passwordValid.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_signUpPwFragment_to_signUpFragment)
        })

        viewModel.passwordInvalid.observe(viewLifecycleOwner, Observer {
            pw_valid.text = "사용할 수 없는 비밀번호입니다."
            pw_valid.setTextColor(Color.RED)
        })
    }

}