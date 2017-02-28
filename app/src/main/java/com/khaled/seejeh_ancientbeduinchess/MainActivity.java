package com.khaled.seejeh_ancientbeduinchess;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.khaled.seejeh_ancientbeduinchess.java_classes.Board;
import com.khaled.seejeh_ancientbeduinchess.java_classes.GamePhase;
import com.khaled.seejeh_ancientbeduinchess.java_classes.HumanVsComputer;
import com.khaled.seejeh_ancientbeduinchess.java_classes.PlayerTurn;
import com.khaled.seejeh_ancientbeduinchess.java_classes.SquareButton;
import com.khaled.seejeh_ancientbeduinchess.java_classes.SquareStatus;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private HumanVsComputer game;
    boolean firstBuild = false;
    int[] squares = new int[]{
            R.id.b1,
            R.id.b2,
            R.id.b3,
            R.id.b4,
            R.id.b5,
            R.id.b6,
            R.id.b7,
            R.id.b8,
            R.id.b9,
            R.id.b10,
            R.id.b11,
            R.id.b12,
            R.id.b13,
            R.id.b14,
            R.id.b15,
            R.id.b16,
            R.id.b17,
            R.id.b18,
            R.id.b19,
            R.id.b20,
            R.id.b21,
            R.id.b22,
            R.id.b23,
            R.id.b24,
            R.id.b25
    };

    ImageButton[] btns = new ImageButton[squares.length];
    private ImageButton lastPointed;
    private int color;
    Drawable defaultBackGround;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        for (int i = 0; i < squares.length; i++) {
            btns[i] = (ImageButton) findViewById(squares[i]);
            btns[i].setOnClickListener(MainActivity.this);
        }
        defaultBackGround = btns[0].getBackground();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*if (savedInstanceState == null || game == null) {
            game = new Board(this);
            game.newGame();
        }*/
        game = new HumanVsComputer(this ,PlayerTurn.RED_TURN);
        drawBoard();
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
       /* boolean toDraw = false;
        if (view instanceof ImageButton) {
            ImageButton tapped = (ImageButton) view;
            int coordinate = Integer.valueOf(tapped.getTag().toString())-1;
            if (game.phase == GamePhase.BUILD) {
                if (game.build(coordinate))
                    drawBoard();
            }else{
               if (game.isPointed){
                   if (game.isValidMove(coordinate)){
                       if (!game.checkTakes(coordinate,game.board.get(coordinate))){
                           if (game.opponentCanMove()) {
                               game.validMovies++;
                           }else {
                               Toast.makeText(MainActivity.this,"Blocked, Another Turn to current player!",Toast.LENGTH_SHORT).show();
                           }
                       }else {
                           if (game.isThereWinner()){
                               Toast.makeText(MainActivity.this,game.winner==0 ? "Black wins": "Red win",Toast.LENGTH_SHORT).show();
                               finish();
                           }
                       }
                       drawBoard();
                   }else {
                       game.pointedCoor = -1;
                       Toast.makeText(MainActivity.this,"Illegal Move!!",Toast.LENGTH_SHORT).show();
                   }
                   game.isPointed = false;
                   lastPointed.setBackground(defaultBackGround);
               }else{
                   if (game.toPoint(coordinate)){
                       color = tapped.getDrawingCacheBackgroundColor();
                       tapped.setBackgroundColor(Color.GREEN);
                       lastPointed = tapped;
                   }
               }
            }
        }*/
        if (view instanceof ImageButton) {
            ImageButton tapped = (ImageButton) view;
            int coordinate = Integer.valueOf(tapped.getTag().toString()) - 1;
            if (game.phase == GamePhase.BUILD) {
                if (game.build(coordinate))
                    drawBoard();
            }
        }
    }

    private void drawBoard() {

        for (int j = 0; j < game.SIZE ; j++) {
            SquareStatus status = game.board.get(j);

            switch (status) {
                case RED:
                    btns[j].setImageResource(R.drawable.red);
                    break;
                case BlLACK:
                    btns[j].setImageResource(R.drawable.black);
                    break;
                case FORBIDDEN:
                    btns[j].setImageResource(R.drawable.fault);
                    break;
                case EMPTY:
                    btns[j].setImageResource(android.R.color.transparent);
                    break;
            }

        }


    }
}
