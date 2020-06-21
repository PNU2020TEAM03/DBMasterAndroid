package com.example.dbmasterandroid.ui.insert;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbmasterandroid.R;
import com.example.dbmasterandroid.data.dto.ColumnInfoDTO;

import java.util.ArrayList;
import java.util.Dictionary;

public class InserRecyclerAdapter extends RecyclerView.Adapter<InserRecyclerAdapter.CustomViewHolder> {

    private ArrayList<ColumnInfoDTO> mList;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView columnName;
        protected EditText columnData;


        public CustomViewHolder(View view) {
            super(view);
            this.columnName = (TextView) view.findViewById(R.id.tv_insert_listitem_columnName);
            this.columnData = (EditText) view.findViewById(R.id.ed_insert_listitem_columnData);

        }
    }

    public InserRecyclerAdapter(ArrayList<ColumnInfoDTO> list) {
        this.mList = list;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_table_insert_list, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.columnName.setText(mList.get(position).getColumnName());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}