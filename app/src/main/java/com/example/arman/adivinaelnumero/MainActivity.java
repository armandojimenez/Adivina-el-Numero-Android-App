/*
 * Created by Armando Jimenez on 7/9/17 12:16 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 7/9/17 12:11 PM
 */

package com.example.arman.adivinaelnumero;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;



public class MainActivity extends AppCompatActivity {
    //declare variables for MainActivity
    private EditText txtGuess;
    private Button btnGuess;
    private Button btnPlayAgain;
    private TextView lblOutput;
    private TextView lblIntentos;
    private TextView textView7;
    private int theNumber;
    private int counter = 0; //counts the number of tries
    private int limit = 10; //set this to limits the tries
    private ImageView imageView5; //win image
    private ImageView imageView6; // lose image
    private ImageView imageView7; //error image
    private ImageView confetti;
    private ImageView confettitop;

    private ImageView sign1;
    private ImageView sign2;


    public void checkGuess() { //Method that checks if the number is equal, greater or less than the
        //random generated number
        //get the number the user enters
        String theirNumber = txtGuess.getText().toString(); //get the text from theNumber
        String message = "";
        final MediaPlayer cheerSound = MediaPlayer.create(this, R.raw.cheer); //cheer sound declaration
        final MediaPlayer loseSound = MediaPlayer.create(this, R.raw.groan); //lose sound declaration
        final MediaPlayer winPlayAgain = MediaPlayer.create(this, R.raw.winplayagain);
        final MediaPlayer losePlayAgain = MediaPlayer.create(this, R.raw.loseplayagain);


        try { //try in case de parseInt does not retrieve any number

            int guess = Integer.parseInt(theirNumber); //get the int from the String

            //message with number of tries left
            this.counter++; //This will increase on each attempt
            lblIntentos.setText(getString(R.string.sigueAdivinando) + (limit - counter) + getString(R.string.intentosDisponibles));


            if (guess > theNumber) { //too high
                message = guess + getString(R.string.muyAlto);
                lblOutput.setText(message);

            } else if (guess < theNumber) { //too low
                message = guess + getString(R.string.muyBajo);
                lblOutput.setText(message);

            } else if (guess == theNumber) { // user got it right
                lblOutput.setTextColor(Color.YELLOW); // text color set to yellow
                message = guess + getString(R.string.ganasteJuegaDeNuevo);
                cheerSound.start(); //cheer sound
                winPlayAgain.start(); //win voice sound

                lblOutput.setText(message);
                if (counter == 1) { //if user got it on first try
                    lblIntentos.setText(R.string.ganasteUnIntento);
                } else if (counter > 1 && counter < 6) {  //from 2 to 5
                    lblIntentos.setText(getString(R.string.lograste) + counter + getString(R.string.intentosIncreible));
                } else {
                    lblIntentos.setText(getString(R.string.lograste) + counter + getString(R.string.intentosSigue));
                }


                imageView5.setVisibility(View.VISIBLE); //set the win image to visible
                confettitop.setVisibility(View.VISIBLE);
                textView7.setVisibility(View.INVISIBLE);
                confetti.setVisibility(View.VISIBLE);
                sign1.setVisibility(View.INVISIBLE);
                sign2.setVisibility(View.INVISIBLE);

                txtGuess.setEnabled(false); //txtGuess get disabled
                swapButtons(); //method to swap guess button and play again button
            }

            if (counter >= 10 && guess != theNumber) { //if the user does not have tries left
                loseSound.start(); // lose sound
                losePlayAgain.start(); //lose voice sound
                lblIntentos.setTextColor(Color.RED); //text color set to red

                lblIntentos.setText(getString(R.string.youLost) + theNumber + getString(R.string.playAgain));
                imageView6.setVisibility(View.VISIBLE); //lose image set to visible
                txtGuess.setEnabled(false); //txt Guess disables
                swapButtons(); //swap buttons


            }


        } catch (Exception e) { //if the try catch any exceptions
            message = getString(R.string.errorMessage);
            lblOutput.setText(message);
            imageView7.setVisibility(View.VISIBLE); //error image set to visible
        } finally { //highlight the txt Guess field and select all for a better UX


            txtGuess.requestFocus();
            txtGuess.selectAll();
        }


    }


    private void newGame() {
        //this method reset everything and generate a new random number


        theNumber = (int) (Math.random() * 100 + 1); //random number
        txtGuess.setEnabled(true); //enable textGuess
        this.limit = 10; //set the limit back to the default number
        this.counter = 0; //set counter to 0 again
        txtGuess.setText("");
        lblOutput.setTextColor(Color.BLACK); //color reverted to black
        lblIntentos.setTextColor(Color.BLUE);//color reverted to blue
        textView7.setVisibility(View.VISIBLE);
        sign1.setVisibility(View.VISIBLE);
        sign2.setVisibility(View.VISIBLE);

        //set the images to invisible, and the default guess button to visible
        btnPlayAgain.setVisibility(View.INVISIBLE);
        btnGuess.setVisibility(View.VISIBLE);
        imageView5.setVisibility(View.INVISIBLE);
        imageView6.setVisibility(View.INVISIBLE);
        imageView7.setVisibility(View.INVISIBLE);
        confetti.setVisibility(View.INVISIBLE);
        confettitop.setVisibility(View.INVISIBLE);


    }

    private void swapButtons() {
        //swap buttons
        btnGuess.setVisibility(View.INVISIBLE);
        btnPlayAgain.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-3802992379133652~8841973325");


        //click sound creation
        final MediaPlayer clickSound = MediaPlayer.create(this, R.raw.click2);
        final MediaPlayer startSound = MediaPlayer.create(this, R.raw.startsound);
        final MediaPlayer adivinaelnumeronew = MediaPlayer.create(this, R.raw.adivinaelnumeronew);
        final MediaPlayer adivinaElNumero = MediaPlayer.create(this, R.raw.adivinaelnumero);


        //variable initialization, wiring the Ui with the code
        txtGuess = (EditText) findViewById(R.id.txtGuess);
        btnGuess = (Button) findViewById(R.id.btnGuess);
        btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        lblOutput = (TextView) findViewById(R.id.lblOutput);
        lblIntentos = (TextView) findViewById(R.id.lblIntentos);
        textView7 = (TextView) findViewById(R.id.textView7);
        imageView5 = (ImageView) findViewById(R.id.imageView5);
        imageView6 = (ImageView) findViewById(R.id.imageView6);
        imageView7 = (ImageView) findViewById(R.id.imageView7);
        confetti = (ImageView) findViewById(R.id.confetti);
        confettitop = (ImageView) findViewById(R.id.confettitop);
        sign1 = (ImageView) findViewById(R.id.sign1);
        sign2 = (ImageView) findViewById(R.id.sign2);


        newGame(); //start a new game on load
        startSound.start();
        adivinaelnumeronew.start();
        //event listener for Guess button
        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSound.start(); //click sound on each click
                checkGuess(); //checkGuess on each click
            }
        });
        //event listener for Play Again button
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lblOutput.setText(R.string.buenaSuerte);
                lblIntentos.setText(getString(R.string.tienes_tries));
                adivinaElNumero.start();

                newGame();

            }
        });


        //set up listener for input field, when user hit enter
        txtGuess.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                clickSound.start();
                checkGuess();
                return true; //this means the keyboard will stay on
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
