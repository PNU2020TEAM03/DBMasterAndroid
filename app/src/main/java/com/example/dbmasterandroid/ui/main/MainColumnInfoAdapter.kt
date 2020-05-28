package com.example.dbmasterandroid.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dbmasterandroid.R
import kotlinx.android.synthetic.main.item_column_info_list.view.*

class MainColumnInfoAdapter(
        private val viewModel: MainViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_column_info_list, parent, false))
    }

    override fun getItemCount(): Int = viewModel.getColumnInfoListSize()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = viewModel.getColumnInfoListItem(position)

        val primaryKey = item["ispk"]
        val columnName = item["columnName"]
        val dataType = item["datatype"]
        val columnSize = item["columnsize"]

        holder.itemView.column_name.text = columnName
        holder.itemView.data_type_name.text = "$dataType($columnSize)"
        if (primaryKey == "Y") {
            holder.itemView.primary_key.text = "PK"
        } else {
            holder.itemView.primary_key.text = "NONE"
        }
    }
}