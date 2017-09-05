package com.example.jun.sampo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jun on 25/08/2017.
 * provides the high scores for the user.
 */

public class SAMPO_HIGHSCORE extends AppCompatActivity{

    private EditText nameBox;
    private ImageButton confirmButton;
    private TableLayout highscoresTable;
    private TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampo_highscores);

        this.nameBox = (EditText) findViewById(R.id.username);

        this.scoreText = (TextView) findViewById(R.id.score);
        this.scoreText.setText(Integer.toString(SAMPO_MAIN.score));

        this.confirmButton = (ImageButton) findViewById(R.id.confirmButton);
        this.confirmButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //shows all scores.
                Database.addScoreToDatabase(((EditText) findViewById(R.id.username)).getText().toString());
                ArrayList<Integer> highscores = Database.getHighscores();

            }
        });
    }
}
