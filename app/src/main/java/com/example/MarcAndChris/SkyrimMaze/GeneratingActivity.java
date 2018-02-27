package com.example.MarcAndChris.SkyrimMaze;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.os.Handler;
import android.widget.Toast;

import falstad.MazeController;
import falstad.MazePanel;
import falstad.MazeRevisit;
import generation.MazeBuilder;
import generation.Order.Builder;


/**
 * Created by Marc on 11/15/2017.
 * Generating interacts with PlayActivity
 * it waits for the maze to build and
 * switches to PlayActivity when the maze is done building
 * Collaborator:
 *  android.content.Intent;
  android.support.v4.app.FragmentManager;
  android.support.v7.app.AppCompatActivity;
  android.os.Bundle;
  android.util.Log;
  android.view.MenuItem;
  android.view.View;
  android.widget.TextView;
  android.widget.Button;
  android.widget.ProgressBar;
  android.os.Handler;
  android.widget.Toast;

 */

public class GeneratingActivity extends AppCompatActivity {

    private ProgressBar pbar;
    private TextView loading;
    private Button playGame;
    private int status = 0;
    private Handler lhandler = new Handler();
    public MediaPlayer mediaPlayer;
    MazeController controller;
    //private TextView ldc;




    /**
     *
     * @param savedInstanceState
     * creates the view
     * enables and shows back action bar
     * changes functionality of back button to return to title screen
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.MarcAndChris.SkyrimMaze.R.layout.activity_generating);
        controller = new MazeController();

        // mediaPlayer = MediaPlayer.create(this, com.example.MarcAndChris.SkyrimMaze.R.raw.skyrimgenerating);
        // mediaPlayer.setLooping(true);
        // mediaPlayer.start();

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
                        Intent intent = new Intent(GeneratingActivity.this, AMazeActivity.class);
                        startActivity(intent);

                    }
                }
        );



        // loading bar stuff here
        pbar = (ProgressBar) findViewById(com.example.MarcAndChris.SkyrimMaze.R.id.progressbar);
        loading =(TextView) findViewById(com.example.MarcAndChris.SkyrimMaze.R.id.LoadingDone);
        playGame = (Button) findViewById(com.example.MarcAndChris.SkyrimMaze.R.id.playGame);


        new Thread(new Runnable() {
            @Override
            public void run() {
                while(status <100){
                    status++;
                    android.os.SystemClock.sleep(10);
                    lhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            pbar.setProgress(status);

                        }
                    });
                }
                lhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loading.setVisibility(View.VISIBLE);
                        playGame.setVisibility(View.VISIBLE);
                        //int a = MazeRevisit.getSkill();
                        //String b = Integer.toString(a);
                        //loading.setText(b);
                       //int a = MazeRevisit.getBuilder();
                       //String b = Integer.toString(a);
                       //loading.setText(b);
                    }
                });
            }
        }).start();

        controller.setSkillLevel(MazeRevisit.getSkill());

        switch(MazeRevisit.getBuilder()) {
            case 0:
                controller.setBuilder(Builder.DFS);
                break;
            case 1:
                controller.setBuilder(Builder.Eller);
                break;
            case 2:
                controller.setBuilder(Builder.Prim);
                break;
            default:
                controller.setBuilder(Builder.DFS);
                break;

        }










    }


    /**
     * Function that takes item as parameter
     * and if parameter is the back button then
     * it finishes the activity and returns to
     * AMazeActivity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            Log.v("GeneratingActivity","Home Button");
            Toast.makeText(this, "Home Button", Toast.LENGTH_LONG).show();
            //stopMusic();
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * switch to play screen button
     * will be changed to automatic switch
     * for next project
     * @param view
     */
    public void onButtonClick(View view) {
        if (view.getId() == com.example.MarcAndChris.SkyrimMaze.R.id.playGame) {
            Intent intent = new Intent(this, PlayActivity.class);
            startActivity(intent);
            Log.v("GeneratingActivity","Play Maze Button");
            Toast.makeText(this, "Play Maze Button", Toast.LENGTH_LONG).show();
            //stopMusic();
            this.finish();


        }

    }

    private void stopMusic() {
        //    mediaPlayer.pause();
        //    mediaPlayer.stop();
        //    mediaPlayer.release();
    }


    /**
     * get maze stuff use this
     *                 Builder itemPicked;
     switch(i) {
     case 0:
     itemPicked = Builder.DFS;
     break;
     case 1:
     itemPicked = Builder.Eller;
     break;
     case 2:
     itemPicked = Builder.Prim;
     break;
     default:
     itemPicked = Builder.DFS;
     break;

     }
     */
}

