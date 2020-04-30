package com.example.dbmasterandroid.ui.tableselect;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbmasterandroid.R;

public class TableSelect extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --------------------------------------
        // 리사이클러뷰 사용방법 예제
        // --------------------------------------
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //리사이클러뷰에 레이아웃 매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        TableAdapter adapter = new TableAdapter();

        // 아이템 추가
        adapter.addItem(new Table("tableName"));
        adapter.addItem(new Table("개인정보"));
        adapter.addItem(new Table("학생"));
        adapter.addItem(new Table("주소"));
        adapter.addItem(new Table("학교"));


        //리사이클러뷰에 어댑터 설정
        recyclerView.setAdapter(adapter);

        // ---------------------------------------
    }
}
