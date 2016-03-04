package com.snazzis.recom;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RelativeLayout player;
    ImageButton playerPlay;
    ImageButton playerPrev;
    ImageButton playerNext;
    ImageButton playerPause;
    TextView playerCurrent;
    Animation a;
    SeekBar volumeBar;
    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        player = (RelativeLayout) findViewById(R.id.player);
        player.post(new Runnable() {

            @Override
            public void run() {

                player.animate().translationY(drawer.getHeight()-268)
                        .alpha(1.0f);
            }
        });

        playerPlay = (ImageButton) findViewById(R.id.playerPlay);
        playerPrev = (ImageButton) findViewById(R.id.playerPrev);
        playerNext = (ImageButton) findViewById(R.id.playerNext);
        playerPause = (ImageButton) findViewById(R.id.playerPause);
        playerCurrent = (TextView) findViewById(R.id.playerCurrent);
        volumeBar = (SeekBar) findViewById(R.id.volumeBar);
        View.OnClickListener playerButtonClick = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("clicked to play button");
                Integer currentButton = (Integer) v.getId();
                System.out.println(currentButton);
            }
        };
        View.OnClickListener playerTitleClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(drawer.getHeight());
                player.animate().translationY(0)
                        .alpha(1.0f);
            }
        };
        playerCurrent.setOnClickListener(playerTitleClick);
        playerPlay.setOnClickListener(playerButtonClick);
        playerPrev.setOnClickListener(playerButtonClick);
        playerNext.setOnClickListener(playerButtonClick);
        playerPause.setOnClickListener(playerButtonClick);
        SeekBar.OnSeekBarChangeListener volumeChange = new SeekBar.OnSeekBarChangeListener(){
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
        volumeBar.setOnSeekBarChangeListener(volumeChange);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.playlist) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
