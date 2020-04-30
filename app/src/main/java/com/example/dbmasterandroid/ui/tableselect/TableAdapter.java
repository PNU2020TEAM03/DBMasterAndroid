package com.example.dbmasterandroid.ui.tableselect;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbmasterandroid.R;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {
    ArrayList<Table> items = new ArrayList<Table>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        // 인플레이션으로 통해 뷰 객체 만들기
        View itemView = inflater.inflate(R.layout.activity_table_item, viewGroup, false);

        // 뷰홀더 객체를 생성하면서 뷰 객체를 전달하고 그 뷰홀더 객체를 반환
        return new ViewHolder(itemView);
    }

    // 뷰홀더가 재사용될 때 호출
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Table item = items.get(position);
        viewHolder.setItem(item);
    }


    // 전체 아이템이 몇 개 인지 확인 후 반환
    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addItem(Table item) {
        items.add(item);
    }

    public void setItems(ArrayList<Table> items) {
        this.items = items;
    }

    public Table getItem(int position) {
        return items.get(position);
    }

    public Table setItem(int position, Table item) {
        return items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        // 뷰홀더 생성자로 전달되는 뷰 객체 참조
        public ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 들어 있는 텍스트뷰 참조
            textView = itemView.findViewById(R.id.textView);
        }

        public void setItem(Table item) {
            textView.setText(item.getTableTitle());
        }
    }
}
