package com.snazzis.recom;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class LoginFrame extends Fragment {

    final String LOG_TAG = "LoginPage";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, " onCreateView");
        final View view = inflater.inflate(R.layout.activity_login, null);;

        vkLogin = (Button) view.findViewById(R.id.vkLogin);
        View.OnClickListener login = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Auth to VK", Toast.LENGTH_SHORT).show();
                VKSdk.login(getActivity(), sMyScope);
            }
        };
        vkLogin.setOnClickListener(login);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, " onActivityCreated");
    }

    Button vkLogin;
    Activity context = null;
    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.DOCS
    };
    SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Инициализация приложения...
//        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
//                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        editor = sharedPref.edit();
//
//        String vk_token = getResources().getString(R.string.vk_token);
////        if(vk_token.length()>0) {
////            redirectToPlayer();
////        }

    }
    private void redirectToPlayer() {
        Log.d(LOG_TAG,"Redirect to player");
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Log.d("MainActivity","Logged");
                Log.d("MainActivityVkToken",res.accessToken);
                editor.putInt(getString(R.string.vk_token), resultCode);
                editor.commit();
                redirectToPlayer();
            }
            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
