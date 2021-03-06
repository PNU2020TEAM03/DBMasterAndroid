package com.example.dbmasterandroid.ui.main.tabledata

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dbmasterandroid.R
import kotlinx.android.synthetic.main.item_table_row_data.view.*

class TableDataAdapter(
        private val viewModel: TableDataViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_table_row_data, parent, false))
    }

    override fun getItemCount(): Int = viewModel.getTableListSize()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val columnNames = viewModel.getTableColumnNames()
        val rowData = viewModel.getTableListItem(position)
        val rowDataList = ArrayList<String>()

        /* 길게 클릭하면 삭제. */
        holder.itemView.setOnLongClickListener {
            val primaryKey = viewModel.getTablePrimaryKey()
            val primaryData = rowData[primaryKey].toString()

            deleteRowData(it, primaryKey, primaryData)
            false
        }

        /* 짧게 클릭하면 화면 이동 */
        holder.itemView.setOnClickListener {
            val primaryKey = viewModel.getTablePrimaryKey()
            val primaryData = rowData[primaryKey].toString()

            val primaryInfo = HashMap<String, String>()
            primaryInfo["pkName"] = primaryKey
            primaryInfo["pkValue"] = primaryData

            moveToUpdateScreen(it, rowData, primaryInfo)
        }

        for (columnName in columnNames) {
            val data = rowData[columnName].toString()
            rowDataList.add(data)
            Log.d("DATA", "$data / $rowDataList")
        }

        when (columnNames.size) {
            1 -> setRowData(holder.itemView.table_row_data1, rowDataList, 0)
            2 -> {
                setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                setRowData(holder.itemView.table_row_data2, rowDataList, 1)
            }
            3 -> {
                setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                setRowData(holder.itemView.table_row_data3, rowDataList, 2)
            }
            4 -> {
                setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                setRowData(holder.itemView.table_row_data3, rowDataList, 2)
                setRowData(holder.itemView.table_row_data4, rowDataList, 3)
            }
            5 -> {
                setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                setRowData(holder.itemView.table_row_data3, rowDataList, 2)
                setRowData(holder.itemView.table_row_data4, rowDataList, 3)
                setRowData(holder.itemView.table_row_data5, rowDataList, 4)
            }
            6 -> {
                setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                setRowData(holder.itemView.table_row_data3, rowDataList, 2)
                setRowData(holder.itemView.table_row_data4, rowDataList, 3)
                setRowData(holder.itemView.table_row_data5, rowDataList, 4)
                setRowData(holder.itemView.table_row_data6, rowDataList, 5)
            }
            7 -> {
                setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                setRowData(holder.itemView.table_row_data3, rowDataList, 2)
                setRowData(holder.itemView.table_row_data4, rowDataList, 3)
                setRowData(holder.itemView.table_row_data5, rowDataList, 4)
                setRowData(holder.itemView.table_row_data6, rowDataList, 5)
                setRowData(holder.itemView.table_row_data7, rowDataList, 6)
            }
            8 -> {
                setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                setRowData(holder.itemView.table_row_data3, rowDataList, 2)
                setRowData(holder.itemView.table_row_data4, rowDataList, 3)
                setRowData(holder.itemView.table_row_data5, rowDataList, 4)
                setRowData(holder.itemView.table_row_data6, rowDataList, 5)
                setRowData(holder.itemView.table_row_data7, rowDataList, 6)
                setRowData(holder.itemView.table_row_data8, rowDataList, 7)
            }
            9 -> {
                setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                setRowData(holder.itemView.table_row_data3, rowDataList, 2)
                setRowData(holder.itemView.table_row_data4, rowDataList, 3)
                setRowData(holder.itemView.table_row_data5, rowDataList, 4)
                setRowData(holder.itemView.table_row_data6, rowDataList, 5)
                setRowData(holder.itemView.table_row_data7, rowDataList, 6)
                setRowData(holder.itemView.table_row_data8, rowDataList, 7)
                setRowData(holder.itemView.table_row_data9, rowDataList, 8)
            }
            10 -> {
                setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                setRowData(holder.itemView.table_row_data3, rowDataList, 2)
                setRowData(holder.itemView.table_row_data4, rowDataList, 3)
                setRowData(holder.itemView.table_row_data5, rowDataList, 4)
                setRowData(holder.itemView.table_row_data6, rowDataList, 5)
                setRowData(holder.itemView.table_row_data7, rowDataList, 6)
                setRowData(holder.itemView.table_row_data8, rowDataList, 7)
                setRowData(holder.itemView.table_row_data9, rowDataList, 8)
                setRowData(holder.itemView.table_row_data10, rowDataList, 9)
            }
        }
        rowDataList.clear()
    }

    private fun setRowData(textView: TextView, rowData: ArrayList<String>, position: Int) {
        textView.apply {
            visibility = View.VISIBLE
            text = rowData[position]
        }
    }

    private fun deleteRowData(view: View, primaryKey: String, primaryData: String) {
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("데이터 삭제")
        builder.setMessage("해당 데이터를 삭제 하시겠습니까?")
        builder.setCancelable(false)
        builder.setPositiveButton("삭제") { _, _ ->
            viewModel.deleteTableData(primaryKey, primaryData)
        }
        builder.setNegativeButton("취소") { _, _ -> }
        builder.show()
    }

    private fun moveToUpdateScreen(view: View, rowData: HashMap<String, String>, pkInfo: HashMap<String, String>) {
        val bundle = bundleOf("rowData" to rowData, "pkInfo" to pkInfo)
        view.findNavController().navigate(R.id.action_tableDataFragment_to_dataUpdateFragment, bundle)
    }
}