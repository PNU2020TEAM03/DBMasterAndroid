package com.example.dbmasterandroid.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dbmasterandroid.ui.main.MainActivity;
import com.example.dbmasterandroid.network.Network;
import com.example.dbmasterandroid.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_serverURL, ed_id, ed_pw;
    private Button btn_login,btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();
    }

    private void bindUI() {
        ed_serverURL = findViewById(R.id.ed_serverURL);
        ed_id = findViewById(R.id.ed_id);
        ed_pw = findViewById(R.id.ed_pw);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);
    }

    private void login() {
        RequestParams params = new RequestParams();
        params.put("id", ed_id.getText().toString());
        params.put("pw", ed_pw.getText().toString());

        Network.post(this, "/login/app_login", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getString("code").equals("S01")) {
                        //TODO 성공하면 MainActivity 로 넘김 

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();

                    } else if (response.getString("code").equals("E00")) {
                        //서버 신택스
                        Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();

                    } else if (response.getString("code").equals("E01")) {
                        //input error
                        Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();


                    } else if (response.getString("code").equals("E02")) {
                        //일치하는 ID 없음


                    } else if (response.getString("code").equals("E03")) {
                        //비밀번호 틀림
                        Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();

                    } else if (response.getString("code").equals("E04")) {
                        //이메일로 계정 활성화 안된 계정
                        Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();

                    } else {

                        String message = response.getString("message");
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("attemptLogin", responseString);
            }
        });
    }//attemptlogin

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Toast.makeText(getApplicationContext(), "Login Clicked", Toast.LENGTH_SHORT).show();
                login();
                break;
            case R.id.btn_signup:
                Toast.makeText(getApplicationContext(), "회원가입 Clicked", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
