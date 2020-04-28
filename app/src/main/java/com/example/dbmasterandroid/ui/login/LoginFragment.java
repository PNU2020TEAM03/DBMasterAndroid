package com.example.dbmasterandroid.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.dbmasterandroid.R;
import com.example.dbmasterandroid.network.Network;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText ed_serverURL, ed_id, ed_pw;
    private Button btn_login,btn_signup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        bindUI(view);
        return view;
    }

    private void bindUI(View view) {
        ed_serverURL = view.findViewById(R.id.ed_serverURL);
        ed_id = view.findViewById(R.id.ed_id);
        ed_pw = view.findViewById(R.id.ed_pw);

        btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_signup = view.findViewById(R.id.btn_signup);
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

                    } else if (response.getString("code").equals("E00")) {
                        //서버 신택스
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();

                    } else if (response.getString("code").equals("E01")) {
                        //input error
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();


                    } else if (response.getString("code").equals("E02")) {
                        //일치하는 ID 없음


                    } else if (response.getString("code").equals("E03")) {
                        //비밀번호 틀림
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();

                    } else if (response.getString("code").equals("E04")) {
                        //이메일로 계정 활성화 안된 계정
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();

                    } else {

                        String message = response.getString("message");
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext(), "Login Clicked", Toast.LENGTH_SHORT).show();
                login();
                break;
            case R.id.btn_signup:
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment);
                break;
        }

    }
}
