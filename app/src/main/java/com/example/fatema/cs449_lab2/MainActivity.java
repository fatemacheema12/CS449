package com.example.fatema.cs449_lab2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder Bob;

    int strikes = 0;
    int balls = 0;
    int outs= GetOuts();
    String filename = "outFile";
    FileOutputStream outputStream;
    FileInputStream inputStream;

    TextView strikesLabel;
    TextView ballsLabel;
    TextView outsLabel;
    Button strikeButton;
    Button ballButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Bob = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);}
        else {
            Bob = new AlertDialog.Builder(MainActivity.this);
        }


        strikesLabel = findViewById(R.id.strikesLabel);
        ballsLabel = findViewById(R.id.ballsLabel);
        outsLabel = findViewById(R.id.outsLabel);
        strikeButton = findViewById(R.id.strikesButton);
        ballButton = findViewById(R.id.ballsButton);

        outsLabel.setText(String.format("Outs: %d", outs));

        strikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strikesLabel.setText(String.format("Strikes: %d", ++strikes));
                if (strikes > 2) {
                    OutAlert();
                }
            }
        });

        ballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballsLabel.setText(String.format("Balls: %d", ++balls));
                if (balls > 3) {
                    WalkAlert();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_reset:
                Reset();
                return true;
            case R.id.action_about:
                Intent fIntent = new Intent(MainActivity.this, AboutActivity.class);
                try {
                    MainActivity.this.startActivity(fIntent);
                } catch (Exception e) {
                    String excep = e.getMessage();
                    ballsLabel.setText(excep);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void NextBatter() {

        strikes = 0;
        balls = 0;
        strikesLabel.setText("Strikes: 0");
        ballsLabel.setText("Balls: 0");

    }

    private void Reset() {
        strikes = 0;
        balls = 0;
        outs = 0;
        SaveOuts();
        strikesLabel.setText("Strikes: 0");
        ballsLabel.setText("Balls: 0");
        outsLabel.setText("Outs: 0");

    }

    private void OutAlert() {
        Bob.setTitle("Out!").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                outsLabel.setText(String.format("Outs: %d", ++outs));
                SaveOuts();
                NextBatter();

            }

        }).setIcon(android.R.drawable.ic_dialog_alert).show();

    }



    private void WalkAlert() {
        Bob.setTitle("Walk!").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NextBatter();

            }

        }).setIcon(android.R.drawable.ic_dialog_alert).show();

    }

    private void SaveOuts() {

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write((Integer.toString(outs).getBytes()));
            outputStream.close();

        } catch (Exception e) {



        }

    }

    private int GetOuts() {
        StringBuilder text = new StringBuilder();
        try {
            inputStream = openFileInput(filename);
            if (inputStream != null) {
                InputStreamReader inputReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputReader);
                String line;
                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        text.append(line);

                    }
                    bufferedReader.close();
                    return Integer.parseInt(text.toString());

                } catch (Exception e) {



                }

            }



        } catch (Exception e) {



        }

        return 0;

    }

}
