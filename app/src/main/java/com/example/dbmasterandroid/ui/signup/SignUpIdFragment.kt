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
import kotlinx.android.synthetic.main.fragment_signup_id.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpIdFragment : Fragment() {

    private val viewModel: SignUpViewModel by sharedViewModel()
    private var name: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_signup_id, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_signup_next_id.setOnClickListener {
            name = id_input.text.toString()
            viewModel.checkNameValid(name)
        }

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
}