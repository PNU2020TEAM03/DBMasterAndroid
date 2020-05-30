package com.example.dbmasterandroid.ui.signup.emailauth

import android.graphics.Color
import android.view.View
import androidx.lifecycle.Observer
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_signup_email_auth.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpEmailFragment: BaseFragment<SignUpEmailViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_signup_email_auth

    override val viewModel: SignUpEmailViewModel by viewModel()

    override fun initView() {}

    override fun initData() {
        /* 네트워크 문제로 타임아웃 발생하였을 때 또는 subscribe 실패했을 때 */
        viewModel.networkInvalid.observe(viewLifecycleOwner, Observer {
            setEmailValidText(it, Color.RED)
        })
        /* 이메일 형식 맞지 않을 때. (REGEX 체크) */
        viewModel.emailInvalid.observe(viewLifecycleOwner, Observer {
            setEmailValidText(it, Color.RED)
        })
        /* 이메일 형식 맞을 때. (REGEX 체크 및 인증번호 발송) */
        viewModel.emailValid.observe(viewLifecycleOwner, Observer {
            setEmailValidText(it, Color.BLUE)
            email_input.visibility = View.INVISIBLE
            btn_signup_auth_email.visibility = View.INVISIBLE
            auth_input.visibility = View.VISIBLE
            btn_signup_next_email.visibility = View.VISIBLE
        })
    }

    override fun initFinish() {
        btn_signup_auth_email.setOnClickListener {
            val email = HashMap<String, String>()
            email["email"] = email_input.text.toString()
            viewModel.requestEmailAuth(email)
        }
        btn_signup_next_email.setOnClickListener {

        }
    }

    private fun setEmailValidText(text: String, color: Int) {
        email_valid.text = text
        email_valid.setTextColor(color)
    }
}