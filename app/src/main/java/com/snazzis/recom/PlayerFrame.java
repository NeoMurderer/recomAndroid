package com.snazzis.recom;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

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
    private View.OnClickListener playerClose = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            closePlayer();
        }
    };
    private WebSocketClient mWebSocketClient;
    public void closePlayer(){
        int height = playerLayout.getHeight();
        Log.d("PlayerHeight", String.valueOf(height));
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
// ;
            }
        });
    }
    View playerView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, " onCreateView");
        playerView = inflater.inflate(R.layout.activity_player, null);
        ButterKnife.bind(this, playerView);
        initEventListeners();
        connectWebSocket();
        return playerView;

    }
    public int convertPixToDip(int pixel){
        float scale = getResources().getDisplayMetrics().density;
        int dips=(int) ((pixel * scale) + 0.5f);
        return dips;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, " onActivityCreated");

    }
    View.OnClickListener playerButtonClick =new View.OnClickListener() {
            public void onClick(View v) {
                JSONObject message = new JSONObject();;
                System.out.println("clicked to play button");
                Integer currentButton = v.getId();
                System.out.println(currentButton);
                try {
                    message.put("action",currentButton);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendMessage(message);
            }
        };

    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://192.168.0.105:3000/cable?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOjF9.OrBxjjETOmrEMoiKcpQ5BU0AN974bIttqjFScg3KGJQ");
        } catch (URISyntaxException e) {
            e.printStackTrace();

            Log.i("Websocket", "FATAL");
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
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
    public void sendMessage(JSONObject message) {
        mWebSocketClient.send(String.valueOf(message));
    }
//    RelativeLayout player;
//
//    ImageButton playerPlay;
//    ImageButton playerPrev;
//    ImageButton playerNext;
//    ImageButton playerPause;
//    TextView playerCurrent;
//    SeekBar volumeBar;
//    View.OnClickListener playerButtonClick;
//    SeekBar.OnSeekBarChangeListener volumeChange = new SeekBar.OnSeekBarChangeListener(){
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            System.out.println(progress);
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//
//        }
//
//    };
//    View.OnClickListener playerTitleClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            player.animate().translationY(0)
//                    .alpha(1.0f);
//        }
//    };
//    private SharedPreferences.Editor editor;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_player);
//        Context context = this;
//        SharedPreferences sharedPref = context.getSharedPreferences(
//                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        editor = sharedPref.edit();
//        String vk_token = getResources().getString(R.string.vk_token);
//        Log.d("MainActivityVkToken",vk_token);
////        player = (RelativeLayout) findViewById(R.id.player);
////        playerPlay = (ImageButton) findViewById(R.id.playerPlay);
////        playerPrev = (ImageButton) findViewById(R.id.playerPrev);
////        playerNext = (ImageButton) findViewById(R.id.playerNext);
////        playerPause = (ImageButton) findViewById(R.id.playerPause);
////        playerCurrent = (TextView) findViewById(R.id.playerCurrent);
////        volumeBar = (SeekBar) findViewById(R.id.volumeBar);
////        playerButtonClick = new View.OnClickListener() {
////            public void onClick(View v) {
////                System.out.println("clicked to play button");
////                Integer currentButton = v.getId();
////                System.out.println(currentButton);
////            }
////        };
////        System.out.println("Hello workls");
//////        playerCurrent.setOnClickListener(playerTitleClick);
//////        playerPlay.setOnClickListener(playerButtonClick);
//////        playerPrev.setOnClickListener(playerButtonClick);
//////        playerNext.setOnClickListener(playerButtonClick);
//////        playerPause.setOnClickListener(playerButtonClick);
//////        volumeBar.setOnSeekBarChangeListener(volumeChange);
////        player.post(new Runnable() {
////
////            @Override
////            public void run() {
////                int height = pxToDp(player.getHeight());
////                Log.d("TEST", String.valueOf(height));
////            }
////        });
//    }
//    public int pxToDp(int px) {
//        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
//        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
//        return dp;
//    }
//    private void initVariables() {
//    }
//    private void initListeners(){
//
//    }
}
