package com.example.dbmasterandroid.ui.setting

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.utils.LoadingIndicator
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment: Fragment() {

    private val viewModel: SettingViewModel by viewModel()
    private var mLoadingIndicator: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        mLoadingIndicator = context?.let { LoadingIndicator(it) }

        return inflater.inflate(R.layout.fragment_main, container, false)
    }
}