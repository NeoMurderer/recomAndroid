package com.snazzis.recom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayerFrame extends Fragment {
    final String LOG_TAG = "PlayerPage";
    @Bind(R.id.playerPlay) ImageButton playerPlay;
    @Bind(R.id.playerPrev) ImageButton playerPrev;
    @Bind(R.id.playerNext) ImageButton playerNext;
    @Bind(R.id.playerPause) ImageButton playerPause;
    @Bind(R.id.playerPlayHead) ImageButton playerPlayHead;
    @Bind(R.id.closePlayer) ImageButton closePlayer;
    @Bind(R.id.playerCurrent) TextView playerCurrent;
    @Bind(R.id.volumeBar) SeekBar volumeBar;
    @Bind(R.id.playerLayout) RelativeLayout playerLayout;
    @Bind(R.id.playerHead) LinearLayout playerHead;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    View playerView;
    SeekBar.OnSeekBarChangeListener volumeChange =new SeekBar.OnSeekBarChangeListener(){
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            System.out.println(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

    };
    View.OnClickListener playerTitleClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPlayer();
        }
    };
    View.OnClickListener playerClose = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            closePlayer();
        }
    };
    WebSocketClient mWebSocketClient;
    private java.lang.String accessToken;

    public void closePlayer(){
        int height = playerLayout.getHeight();
        playerLayout.animate().translationY(height - playerHead.getHeight());
        closePlayer.setVisibility(View.GONE);
        playerPlayHead.setVisibility(View.VISIBLE);

    }
    public void showPlayer(){
        playerPlayHead.setVisibility(View.GONE);
        closePlayer.setVisibility(View.VISIBLE);
        playerLayout.animate().translationY(0)
                .alpha(1.0f);
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, " onCreate");

    }

    private void initEventListeners() {
        playerCurrent.setOnClickListener(playerTitleClick);
        playerPlay.setOnClickListener(playerButtonClick);
        playerPrev.setOnClickListener(playerButtonClick);
        playerNext.setOnClickListener(playerButtonClick);
        playerPause.setOnClickListener(playerButtonClick);
        playerPlayHead.setOnClickListener(playerButtonClick);
        closePlayer.setOnClickListener(playerClose);
        volumeBar.setOnSeekBarChangeListener(volumeChange);
        playerLayout.post(new Runnable() {
            @Override
            public void run() {
                closePlayer();
            }
        });
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView");
        playerView = inflater.inflate(R.layout.activity_player, null);
        ButterKnife.bind(this, playerView);
        initEventListeners();

        sharedPref = getActivity().getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        accessToken = sharedPref.getString(getString(R.string.auth_token), "emptyToken");

        Log.d("Access token",accessToken);
        connectWebSocket();
        return playerView;

    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    View.OnClickListener playerButtonClick =new View.OnClickListener() {
            public void onClick(View v) {
                String currentButton = v.getResources().getResourceName(v.getId());
                try {
                    sendMessage(currentButton);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://192.168.0.105:3000/cable?token="+accessToken);
        } catch (URISyntaxException e) {
            e.printStackTrace();

            Log.i("Websocket", "FATAL");
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                mWebSocketClient.send("{\"command\":\"subscribe\",\"identifier\":\"{\\\"channel\\\":\\\"EventsChannel\\\"}\"}");
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                playerView.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Player frame", message);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }
    public void sendMessage(String message) throws IOException, JSONException {
        AjaxRequest requester = new AjaxRequest();
        requester.accessToken = accessToken;
        JSONObject obj = new JSONObject();

        obj.put("action",message);
        obj.put("platform","mobile");

        String response = requester.post("/fire", obj.toString());
    }

}
