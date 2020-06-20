package com.example.dbmasterandroid.ui.main.tabledata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dbmasterandroid.R
import kotlinx.android.synthetic.main.item_table_row_data.view.*

class TableDataAdapter(
        private val viewModel: TableDataViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_table_row_data, parent, false))
    }

    override fun getItemCount(): Int = viewModel.getTableListSize()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val columnNames = viewModel.getTableColumnNames()
        val rowData = viewModel.getTableListItem(position)
        val rowDataList = ArrayList<String>()

        if (position == 0) {
            when (columnNames.size) {
                1->setColumnData(holder.itemView.table_row_data1, columnNames, 0)
                2->{
                    setColumnData(holder.itemView.table_row_data1, columnNames, 0)
                    setColumnData(holder.itemView.table_row_data2, columnNames, 1)
                }
                3->{
                    setColumnData(holder.itemView.table_row_data1, columnNames, 0)
                    setColumnData(holder.itemView.table_row_data2, columnNames, 1)
                    setColumnData(holder.itemView.table_row_data3, columnNames, 2)
                }
                4->{
                    setColumnData(holder.itemView.table_row_data1, columnNames, 0)
                    setColumnData(holder.itemView.table_row_data2, columnNames, 1)
                    setColumnData(holder.itemView.table_row_data3, columnNames, 2)
                    setColumnData(holder.itemView.table_row_data4, columnNames, 3)
                }
                5->{
                    setColumnData(holder.itemView.table_row_data1, columnNames, 0)
                    setColumnData(holder.itemView.table_row_data2, columnNames, 1)
                    setColumnData(holder.itemView.table_row_data3, columnNames, 2)
                    setColumnData(holder.itemView.table_row_data4, columnNames, 3)
                    setColumnData(holder.itemView.table_row_data5, columnNames, 4)
                }
                6->{
                    setColumnData(holder.itemView.table_row_data1, columnNames, 0)
                    setColumnData(holder.itemView.table_row_data2, columnNames, 1)
                    setColumnData(holder.itemView.table_row_data3, columnNames, 2)
                    setColumnData(holder.itemView.table_row_data4, columnNames, 3)
                    setColumnData(holder.itemView.table_row_data5, columnNames, 4)
                    setColumnData(holder.itemView.table_row_data6, columnNames, 5)
                }
                7->{
                    setColumnData(holder.itemView.table_row_data1, columnNames, 0)
                    setColumnData(holder.itemView.table_row_data2, columnNames, 1)
                    setColumnData(holder.itemView.table_row_data3, columnNames, 2)
                    setColumnData(holder.itemView.table_row_data4, columnNames, 3)
                    setColumnData(holder.itemView.table_row_data5, columnNames, 4)
                    setColumnData(holder.itemView.table_row_data6, columnNames, 5)
                    setColumnData(holder.itemView.table_row_data7, columnNames, 6)
                }
                8->{
                    setColumnData(holder.itemView.table_row_data1, columnNames, 0)
                    setColumnData(holder.itemView.table_row_data2, columnNames, 1)
                    setColumnData(holder.itemView.table_row_data3, columnNames, 2)
                    setColumnData(holder.itemView.table_row_data4, columnNames, 3)
                    setColumnData(holder.itemView.table_row_data5, columnNames, 4)
                    setColumnData(holder.itemView.table_row_data6, columnNames, 5)
                    setColumnData(holder.itemView.table_row_data7, columnNames, 6)
                    setColumnData(holder.itemView.table_row_data8, columnNames, 7)
                }
                9->{
                    setColumnData(holder.itemView.table_row_data1, columnNames, 0)
                    setColumnData(holder.itemView.table_row_data2, columnNames, 1)
                    setColumnData(holder.itemView.table_row_data3, columnNames, 2)
                    setColumnData(holder.itemView.table_row_data4, columnNames, 3)
                    setColumnData(holder.itemView.table_row_data5, columnNames, 4)
                    setColumnData(holder.itemView.table_row_data6, columnNames, 5)
                    setColumnData(holder.itemView.table_row_data7, columnNames, 6)
                    setColumnData(holder.itemView.table_row_data8, columnNames, 7)
                    setColumnData(holder.itemView.table_row_data9, columnNames, 8)
                }
                10->{
                    setColumnData(holder.itemView.table_row_data1, columnNames, 0)
                    setColumnData(holder.itemView.table_row_data2, columnNames, 1)
                    setColumnData(holder.itemView.table_row_data3, columnNames, 2)
                    setColumnData(holder.itemView.table_row_data4, columnNames, 3)
                    setColumnData(holder.itemView.table_row_data5, columnNames, 4)
                    setColumnData(holder.itemView.table_row_data6, columnNames, 5)
                    setColumnData(holder.itemView.table_row_data7, columnNames, 6)
                    setColumnData(holder.itemView.table_row_data8, columnNames, 7)
                    setColumnData(holder.itemView.table_row_data9, columnNames, 8)
                    setColumnData(holder.itemView.table_row_data10, columnNames, 9)
                }
            }
        } else {
            for (columnName in columnNames) {
                val data = rowData[columnName].toString()
                rowDataList.add(data)
            }
            when (columnNames.size) {
                1->setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                2->{
                    setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                    setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                }
                3->{
                    setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                    setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                    setRowData(holder.itemView.table_row_data3, rowDataList, 2)
                }
                4->{
                    setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                    setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                    setRowData(holder.itemView.table_row_data3, rowDataList, 2)
                    setRowData(holder.itemView.table_row_data4, rowDataList, 3)
                }
                5->{
                    setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                    setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                    setRowData(holder.itemView.table_row_data3, rowDataList, 2)
                    setRowData(holder.itemView.table_row_data4, rowDataList, 3)
                    setRowData(holder.itemView.table_row_data5, rowDataList, 4)
                }
                6->{
                    setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                    setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                    setRowData(holder.itemView.table_row_data3, rowDataList, 2)
                    setRowData(holder.itemView.table_row_data4, rowDataList, 3)
                    setRowData(holder.itemView.table_row_data5, rowDataList, 4)
                    setRowData(holder.itemView.table_row_data6, rowDataList, 5)
                }
                7->{
                    setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                    setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                    setRowData(holder.itemView.table_row_data3, rowDataList, 2)
                    setRowData(holder.itemView.table_row_data4, rowDataList, 3)
                    setRowData(holder.itemView.table_row_data5, rowDataList, 4)
                    setRowData(holder.itemView.table_row_data6, rowDataList, 5)
                    setRowData(holder.itemView.table_row_data7, rowDataList, 6)
                }
                8->{
                    setRowData(holder.itemView.table_row_data1, rowDataList, 0)
                    setRowData(holder.itemView.table_row_data2, rowDataList, 1)
                    setRowData(holder.itemView.table_row_data3, rowDataList, 2)
                    setRowData(holder.itemView.table_row_data4, rowDataList, 3)
                    setRowData(holder.itemView.table_row_data5, rowDataList, 4)
                    setRowData(holder.itemView.table_row_data6, rowDataList, 5)
                    setRowData(holder.itemView.table_row_data7, rowDataList, 6)
                    setRowData(holder.itemView.table_row_data8, rowDataList, 7)
                }
                9->{
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
                10->{
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
        }
    }

    private fun setColumnData(textView: TextView, columnNames: MutableSet<String>, position: Int) {
        textView.apply {
            visibility = View.VISIBLE
            text = columnNames.elementAt(position)
        }
    }

    private fun setRowData(textView: TextView, rowData: ArrayList<String>, position: Int) {
        textView.apply {
            visibility = View.VISIBLE
            text = rowData[position]
        }
    }
}