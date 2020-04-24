package com.example.dbmasterandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_serverURL, ed_id, ed_pw;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();
    }

    private void bindUI(){
        ed_serverURL = findViewById(R.id.ed_serverURL);
        ed_id = findViewById(R.id.ed_id);
        ed_pw = findViewById(R.id.ed_pw);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                Toast.makeText(getApplicationContext(),"Login Clicked",Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
