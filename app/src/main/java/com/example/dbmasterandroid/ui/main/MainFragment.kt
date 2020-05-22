package com.example.dbmasterandroid.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dbmasterandroid.MainActivityApplication
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.utils.LoadingIndicator
import kotlinx.android.synthetic.main.main_drawer_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private var mLoadingIndicator: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        mLoadingIndicator = context?.let { LoadingIndicator(it) }

        viewModel.getAllTableData()

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivityApplication

        mainActivity.setUserTableName(viewModel.getUserName(), viewModel.getTableName())

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