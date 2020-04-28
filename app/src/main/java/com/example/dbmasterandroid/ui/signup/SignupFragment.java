package com.example.dbmasterandroid.ui.signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbmasterandroid.R;
import com.example.dbmasterandroid.network.Network;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignupFragment extends Fragment implements View.OnClickListener {

    private EditText signup_id, signup_pw, signup_confirmpw;
    private TextView text_checkpw;
    private Button btn_checkid, btn_signup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        bindUI(view);
        return view;
    }

    private void bindUI(View view) {
        signup_id = view.findViewById(R.id.signup_id);
        signup_pw = view.findViewById(R.id.signup_pw);
        signup_confirmpw = view.findViewById(R.id.signup_confirmpw);

        text_checkpw = view.findViewById(R.id.checkpw);

        btn_checkid = view.findViewById(R.id.btn_checkid);
        btn_checkid.setOnClickListener(this);
        btn_signup = view.findViewById(R.id.btnSignup);
        btn_signup.setOnClickListener(this);
    }

    private void confirmpw() {
        signup_confirmpw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw = signup_pw.getText().toString();
                String confirmpw = signup_confirmpw.getText().toString();

                if(pw.equals(confirmpw)) {
                    text_checkpw.setText("비밀번호 일치");
                    text_checkpw.setTextColor(Color.GREEN);
                }
                else {
                    text_checkpw.setText("비밀번호 불일치");
                    text_checkpw.setTextColor(Color.RED);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignup:
                Toast.makeText(getContext(), "회원가입 Clicked", Toast.LENGTH_SHORT).show();
                signup();
                break;
            case R.id.btn_checkid:
                Toast.makeText(getContext(), "사용할 수 없는 ID입니다", Toast.LENGTH_SHORT).show();
        }
    }

    private void signup() {
        RequestParams params = new RequestParams();
        params.put("id", signup_id.getText().toString());
        params.put("pw", signup_pw.getText().toString());

        Network.post(this,"", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }
        });
    }
}
