package com.example.MarcAndChris.SkyrimMaze;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

import falstad.MazeRevisit;
import generation.Order.Builder;

/**
 * Interacts with seekbar, spinners, and buttons.
 * it listen to the selected size,drivers and builders
 * when explore is clicked it will use those to create
 * a specific maze.
 * AMazeActivity takes to the GeneratingActivity
 * and it is passed down to PlayActivity
 * when a certain maze is picked
 * Collaborators:
 *  android.support.v7.app.AppCompatActivity;
  android.os.Bundle;
  android.util.Log;
  android.widget.ArrayAdapter;
  android.widget.Spinner;
  android.view.View;
  android.content.Intent;
  android.widget.Toast;

 *
 */
public class AMazeActivity extends AppCompatActivity {

    public MediaPlayer mediaPlayer;
    private SeekBar seekbar;
    private TextView size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.MarcAndChris.SkyrimMaze.R.layout.activity_amaze);

        // mediaPlayer = MediaPlayer.create(this, com.example.MarcAndChris.SkyrimMaze.R.raw.skyrimremix);
        // mediaPlayer.setLooping(true);
        // mediaPlayer.start();


        Spinner builder = (Spinner) findViewById(com.example.MarcAndChris.SkyrimMaze.R.id.builderSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, com.example.MarcAndChris.SkyrimMaze.R.array.builder, com.example.MarcAndChris.SkyrimMaze.R.layout.skyrim_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        builder.setAdapter(adapter);

        Spinner driver = (Spinner) findViewById(com.example.MarcAndChris.SkyrimMaze.R.id.driverSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, com.example.MarcAndChris.SkyrimMaze.R.array.driver, com.example.MarcAndChris.SkyrimMaze.R.layout.skyrim_spinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driver.setAdapter(adapter2);
        size = (TextView) findViewById(R.id.size);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MazeRevisit.setSkill(i);
                size.setText("Maze Skill Level: " + i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        builder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                MazeRevisit.setBuilder(i);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        driver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MazeRevisit.setDriver(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    /**
     * listen to button clicks for
     * generate,explore,revist.
     * Everytime they are clicked it
     * Logs the clicked button and
     * creates a tost for the specific button
     * if generate is clicked then
     * it starts a new activity to switch to generating
     *
     *
     * @param view
     */
    public void onButtonClick(View view) {
        if (view.getId() == R.id.explore) {
            Intent intent = new Intent(this, GeneratingActivity.class);
            // stopMusic();
            startActivity(intent);
            Log.v("PlayActivity","Generate Button");
            Toast.makeText(this, "Generate Button", Toast.LENGTH_LONG).show();

        }
        if (view.getId()== com.example.MarcAndChris.SkyrimMaze.R.id.revisit){
            Log.v("PlayActivity","Revisit Button");
            //stopMusic();
            Toast.makeText(this, "Revisit Button", Toast.LENGTH_LONG).show();
        }
        if (view.getId()== com.example.MarcAndChris.SkyrimMaze.R.id.explore){
            Log.v("PlayActivity","Explore Button");
            //stopMusic();
            Toast.makeText(this, "Explore Button", Toast.LENGTH_LONG).show();
        }





    }

    private void stopMusic() {
//        mediaPlayer.pause();
//        mediaPlayer.stop();
//        mediaPlayer.release();
    }



}
