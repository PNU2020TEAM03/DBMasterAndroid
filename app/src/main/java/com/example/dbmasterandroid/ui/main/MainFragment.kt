package com.example.dbmasterandroid.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.utils.PreferenceUtil
import kotlinx.android.synthetic.main.item_main_drawer.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel.getUserName()

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userNameLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                drawer_id_tv.text = it.toString()
            }
        })
    }
}