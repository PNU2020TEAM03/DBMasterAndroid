package com.example.dbmasterandroid.network;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.example.dbmasterandroid.utils.Useful;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Locale;

public class Network {
    //TODO URL 바꿔야함
    public static String DEV_URL = "http://100.10.30.01";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(Activity act, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if(Useful.isNetworkConnected(act) == false){
            Useful.showAlertDialog(act, "알림", "네트워크에 연결되어 있지 않습니다.\n네트워크 연결 후 다시 시도해 주세요.");
            return;
        }
        client.get(DEV_URL + url, params, responseHandler);
    }

    public static void get(Fragment fragment, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if(Useful.isNetworkConnected(fragment.getContext()) == false) {
            Useful.showAlertDialog(fragment, "알림", "네트워크에 연결되어 있지 않습니다.\n네트워크 연결 후 다시 시도해 주세요.");
            return;
        }
        client.get(DEV_URL + url, params, responseHandler);
    }

    public static void post(Activity act, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if(Useful.isNetworkConnected(act) == false){
            Useful.showAlertDialog(act, "알림", "네트워크에 연결되어 있지 않습니다.\n네트워크 연결 후 다시 시도해 주세요.");
            return;
        }
        if(params!=null){
            String countryCode = Locale.getDefault().getCountry();
            params.put("country_code", countryCode);
            params.put("order_device", "pos");
        }
        client.post(DEV_URL + url, params, responseHandler);
    }
}
