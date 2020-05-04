package com.example.dbmasterandroid.ui.tableselect;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbmasterandroid.R;

import java.util.ArrayList;

// 어댑터 클래스가 새로 정의한 리스너 인터페이스 구현하도록 하기
public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> implements onTableItemClickListener {
    ArrayList<Table> items = new ArrayList<Table>();
    onTableItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        // 인플레이션으로 통해 뷰 객체 만들기
        View itemView = inflater.inflate(R.layout.item_table_select_list, viewGroup, false);

        // 뷰홀더 객체를 생성하면서 뷰 객체를 전달하고 그 뷰홀더 객체를 반환
        return new ViewHolder(itemView, this);
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

    // 외부에서 리스너를 설정할 수 있도록 메서드 추가
    public void setOnItemClickListener(onTableItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null) {
            listener.onItemClick(holder, view, position);
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        // 뷰홀더 생성자로 전달되는 뷰 객체 참조
        public ViewHolder(View itemView, final onTableItemClickListener listener) {
            super(itemView);

            // 뷰 객체에 들어 있는 텍스트뷰 참조
            textView = itemView.findViewById(R.id.textView);

            // 아이템 뷰에 onClickListener 설정
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    //아이템 뷰 클릭시 미리 정의한 다른 리스너의 메서드 호출
                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(Table item) {
            textView.setText(item.getTableName());
        }
    }
}
