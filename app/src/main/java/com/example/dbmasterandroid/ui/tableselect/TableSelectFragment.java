package com.example.dbmasterandroid.ui.tableselect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbmasterandroid.R;

public class TableSelectFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_table_select, container, false);
        bindUI(view);
        return view;
    }

    private void bindUI(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        TableAdapter adapter = new TableAdapter();

        adapter.addItem(new Table("tableName"));
        adapter.addItem(new Table("개인정보"));
        adapter.addItem(new Table("학생"));
        adapter.addItem(new Table("주소"));
        adapter.addItem(new Table("학교"));


        //리사이클러뷰에 어댑터 설정
        recyclerView.setAdapter(adapter);
    }
}
