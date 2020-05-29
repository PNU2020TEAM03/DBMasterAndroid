package com.example.dbmasterandroid.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/* TODO Fragment For Single Activity */
class SplashFragment : BaseFragment<SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModel()

    override val layoutResourceId: Int
        get() = R.layout.fragment_splash

    override fun initView() {
        viewModel.getSplashNavigationMode()
    }

    override fun initData() {
        viewModel.splashNavigationModeLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                1 -> {
                    Handler().postDelayed( Runnable {
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    }, 2000L)
                }
            }
        })
    }

    override fun initFinish() {}
}