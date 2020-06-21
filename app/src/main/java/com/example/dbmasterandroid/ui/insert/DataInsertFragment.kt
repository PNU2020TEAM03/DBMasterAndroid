package com.example.dbmasterandroid.ui.insert

import androidx.recyclerview.widget.RecyclerView
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.base.BaseFragment
import com.example.dbmasterandroid.data.dto.ColumnInfoDTO
import kotlinx.android.synthetic.main.fragment_data_insert.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataInsertFragment: BaseFragment<DataInsertViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_data_insert


    override val viewModel: DataInsertViewModel by viewModel()

    override fun initView() {
        val sampleColumnArrayList = ArrayList<ColumnInfoDTO>()
        val item1 = ColumnInfoDTO("예시칼럼1","정수","12","ㄷㄱ")
        val item2 = ColumnInfoDTO("예시칼럼2","정수","12","ㄷㄱ")
        val item3 = ColumnInfoDTO("예시칼럼3","정수","12","ㄷㄱ")
        sampleColumnArrayList.add(item1)
        sampleColumnArrayList.add(item2)
        sampleColumnArrayList.add(item3)


        var adapter = InserRecyclerAdapter(sampleColumnArrayList)
        recycler_insert.adapter = adapter
    }

    override fun initData() {}

    override fun initFinish() {}
}