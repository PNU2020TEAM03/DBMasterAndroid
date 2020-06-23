package com.example.dbmasterandroid.ui.insert

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dbmasterandroid.R
import kotlinx.android.synthetic.main.item_table_insert_list.view.*

class DataInsertAdapter(
        private val viewModel: DataInsertViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_table_insert_list, parent, false))
    }

    override fun getItemCount(): Int = viewModel.getTableInfoListSize()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = viewModel.getTableInfoListItem(position)

        holder.itemView.tv_insert_listitem_columnName.text = item["columnName"]
        holder.itemView.tv_insert_listitem_columnData.text = "${item["datatype"]}(${item["columnsize"]})"
        if (item["ispk"] == "Y") {
            holder.itemView.tv_insert_listitem_columnName.setTextColor(Color.BLUE)
            holder.itemView.tv_insert_listitem_columnData.setTextColor(Color.BLUE)
        }
    }
}