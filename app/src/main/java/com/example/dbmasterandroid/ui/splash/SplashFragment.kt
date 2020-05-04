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

/* TODO Fragment For Single Activity */
class SplashFragment : Fragment() {

    private val viewModel = SplashViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel.getSplashNavigationMode()
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}