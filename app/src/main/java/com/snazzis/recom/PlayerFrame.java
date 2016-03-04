package com.snazzis.recom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayerFrame extends Fragment {
    final String LOG_TAG = "PlayerPage";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, " onCreate");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, " onCreateView");
        return inflater.inflate(R.layout.activity_player, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, " onActivityCreated");
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
