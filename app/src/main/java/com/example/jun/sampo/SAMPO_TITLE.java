package com.example.jun.sampo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

//TODO make the title have all sorts of different objects through it. Tiles of the four colours, red, green, blue, and yellow. And they are changing colour.

public class SAMPO_TITLE extends AppCompatActivity implements View.OnClickListener{

    private Button buttonPlay;
    private TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampo__title);

        //setting orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //getting the button
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);

        scoreText = (TextView) findViewById(R.id.score);
        scoreText.setText("Score: " + SAMPO_MAIN.score);

    }

    @Override
    public void onClick(View v){
        //starting game activity
        startActivity(new Intent(this, SAMPO_MAIN.class));
    }
}
