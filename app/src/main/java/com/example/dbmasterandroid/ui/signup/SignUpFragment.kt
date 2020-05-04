package com.example.dbmasterandroid.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import kotlinx.android.synthetic.main.fragment_signup.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sign_up_complete_label1.text = "${viewModel.currentID} 님"

        btn_signup_complete.setOnClickListener {
            viewModel.signUp()
        }

        viewModel.signUpValid.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            makeText(context, "회원가입에 성공했습니다.", LENGTH_SHORT).show()
        })

        viewModel.signUpInvalid.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            makeText(context, "회원가입에 실패했습니다. 문의 바랍니다.", LENGTH_SHORT).show()
        })

        viewModel.networkInvalid.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            makeText(context, "서버 또는 네트워크 문제입니다.", LENGTH_SHORT).show()
        })
    }
}