package com.example.dbmasterandroid.ui.login

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.utils.LoadingIndicator
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: Fragment() {

    private val viewModel: LoginViewModel by viewModel()
    private var name: String? = null
    private var pw: String? = null
    private var mLoadingIndicator: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        mLoadingIndicator = context?.let { LoadingIndicator(it) }

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        viewModel.startLoadingLiveData.observe(viewLifecycleOwner, Observer {
            startLoadingIndicator()
        })

        viewModel.stopLoadingLiveData.observe(viewLifecycleOwner, Observer {
            stopLoadingIndicator()
        })
    }

    private fun stopLoadingIndicator() {
        mLoadingIndicator?.let {
            if (it.isShowing) it.cancel()
        }
    }

    private fun startLoadingIndicator() {
        stopLoadingIndicator()
        activity?.let {
            if (!it.isFinishing) {
                mLoadingIndicator = LoadingIndicator(requireContext())
                mLoadingIndicator?.show()
            }
        }
    }
}