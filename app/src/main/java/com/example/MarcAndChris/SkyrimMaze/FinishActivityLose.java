package com.example.MarcAndChris.SkyrimMaze;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by markxsimu on 11/20/17.
 * Ends screen if the user does not complete the maze
 * allows the user to return to the main menu with the
 * Menu button
 * Collaborators:
 *  android.content.Intent;
  android.os.Bundle;
  android.support.v4.app.FragmentManager;
  android.support.v7.app.AppCompatActivity;
  android.util.Log;
  android.view.MenuItem;
  android.view.View;
  android.widget.Toast;
 */

public class FinishActivityLose extends AppCompatActivity {

    public MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.MarcAndChris.SkyrimMaze.R.layout.activity_finishlose);

        // mediaPlayer = MediaPlayer.create(this, com.example.MarcAndChris.SkyrimMaze.R.raw.alduinshout);
        //  mediaPlayer.setLooping(true);
        //  mediaPlayer.start();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    /**
                     * Changes activity to AMazeActivity when back
                     * button is pressed
                     */
                    @Override
                    public void onBackStackChanged() {
                        Intent intent = new Intent(FinishActivityLose.this, AMazeActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }

    /**
     * Listen for button lick
     * on loseback button
     * and when loseback button is clicked
     * it ends this activity and returns back
     * to AMazeActivity
     * also creates log and toast
     * @param view
     */
    public void onButtonClick(View view) {
        if (view.getId() == com.example.MarcAndChris.SkyrimMaze.R.id.loseback) {
            Intent intent = new Intent(this, AMazeActivity.class);
            startActivity(intent);
            Log.v("FinishActivityLose","Menu Button");
            Toast.makeText(this, "Menu Button", Toast.LENGTH_LONG).show();
            //    stopMusic();
            this.finish();
        }

    }

    /**
     *  listen for back button
     *  and when it is clicked it
     *  returns to the AMazeActivity
     *  creates log and toast
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            Log.v("FinishActivityLose","Home Button");
            Toast.makeText(this, "Home Button", Toast.LENGTH_LONG).show();
            //    stopMusic();
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void stopMusic() {
        //  mediaPlayer.pause();
        //  mediaPlayer.stop();
        //  mediaPlayer.release();
    }

}