package com.example.dbmasterandroid.ui.table.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dbmasterandroid.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TableColumnTypeFragment: Fragment() {

    private val viewModel: TableCreateViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_column_create_type, container, false)
    }
}