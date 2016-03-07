package com.snazzis.recom;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginFrame extends Fragment {

    final String LOG_TAG = "LoginPage";

    @Bind(R.id.vkLogin) Button vkLogin;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView");
        View loginView = inflater.inflate(R.layout.activity_login, null);;

        ButterKnife.bind(this, loginView);

        View.OnClickListener login = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Login to VK");
                VKSdk.login(getActivity(), sMyScope);
            }
        };
        vkLogin.setOnClickListener(login);
        return loginView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, "onActivityCreated");
    }
    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.DOCS
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
