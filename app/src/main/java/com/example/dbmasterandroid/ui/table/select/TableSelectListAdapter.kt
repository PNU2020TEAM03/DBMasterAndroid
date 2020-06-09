package com.example.dbmasterandroid.ui.table.select

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dbmasterandroid.MainActivity
import com.example.dbmasterandroid.R
import kotlinx.android.synthetic.main.item_table_select_list.view.*

class TableSelectListAdapter(
        private val viewModel: TableSelectViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_table_select_list, parent, false))
    }

    override fun getItemCount(): Int = viewModel.getTableSize()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tableItem = viewModel.getTableItem(position)

        holder.itemView.table_name.text = tableItem
        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_tableSelectFragment_to_mainFragment)
            MainActivity.preferences.setName("tableName", tableItem)

            val dbname = MainActivity.preferences.getName("dbName", "nodb")
            val tablename = MainActivity.preferences.getName("tableName", "notable")

            Log.e("DB TABLE", "$dbname / $tablename")
        }
    }
}