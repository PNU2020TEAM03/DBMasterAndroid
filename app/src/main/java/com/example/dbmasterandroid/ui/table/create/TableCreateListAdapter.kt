package com.example.dbmasterandroid.ui.table.create

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dbmasterandroid.R
import com.example.dbmasterandroid.utils.Useful
import kotlinx.android.synthetic.main.item_table_create_list.view.*

class TableCreateListAdapter(
        private val viewModel: TableCreateViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_table_create_list, parent, false))
    }

    override fun getItemCount(): Int = viewModel.getColumnListSize()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = viewModel.getColumnListItem(position)

        holder.itemView.column_name.text = item.columnName
        if (item.columnSize != "0") {
            holder.itemView.data_type_name.text = "${item.columnType}(${item.columnSize})"
        } else {
            holder.itemView.data_type_name.text = "${item.columnType}"
        }
        holder.itemView.primary_or_foreign_key.text = item.columnKey
        holder.itemView.setOnClickListener {
            Log.e("RECYCLER VIEW", item.columnName)
            val builder = AlertDialog.Builder(it.context)
            builder.setTitle("칼럼 삭제")
            builder.setMessage("해당 칼럼을 삭제 하시겠습니까?")
            builder.setCancelable(false)
            builder.setPositiveButton("삭제") { _, _ ->
                viewModel.deleteColumnItem(position)
            }
            builder.setNegativeButton("취소") { _, _ -> }
            builder.show()
        }
    }

}