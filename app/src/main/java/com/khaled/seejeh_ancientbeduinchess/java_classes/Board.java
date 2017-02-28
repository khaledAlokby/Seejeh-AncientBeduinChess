package com.khaled.seejeh_ancientbeduinchess.java_classes;

import android.content.Context;
import android.util.Pair;
import android.widget.Toast;

import com.khaled.seejeh_ancientbeduinchess.MainActivity;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khaled on 12/12/2016.
 */

public class Board {

    final static int LENGTH = 5;
    public List<SquareStatus> board = new ArrayList<>();
    PlayerTurn turn;
    public GamePhase phase;
    public int SIZE;
    public int validMovies = 0;
    public boolean isPointed = false;
    public final static int UP = (-1)*LENGTH;
    public final static int DOWN = LENGTH;
    public final static int RIGHT = 1;
    public final static int LEFT = (-1);
    public int pointedCoor = -1;
    private int winnerChecker = -1;
    Context context;
    public int winner;

    public Board(Context mainActivity) {
        context = mainActivity;
        SIZE = LENGTH*LENGTH;
        for (int i = 0; i < SIZE; i++) {
             board.add(null);
        }
        newGame();
    }

    private void createEmptyBoard() {
        for (int i = 0; i < SIZE ; i++){
            board.set(i,SquareStatus.EMPTY);
        }
    }



    public boolean isInRange(int coordinate){
        return coordinate < SIZE && coordinate > 0;
    }

    public void newGame(){
        createEmptyBoard();
        board.set(SIZE / 2 ,SquareStatus.FORBIDDEN);
        turn = PlayerTurn.BLACK_TURN;
        phase = GamePhase.BUILD;
    }

    public boolean build(int coordinate){
        if (board.get(coordinate) == SquareStatus.EMPTY) {
            if (turnOf() == PlayerTurn.BLACK_TURN) {
                board.set(coordinate, SquareStatus.BlLACK);
            }else {
                board.set(coordinate, SquareStatus.RED);
            }
            validMovies++;
            if (validMovies == SIZE-1){
                board.set(SIZE / 2 ,SquareStatus.EMPTY);
                turn = PlayerTurn.RED_TURN;
                if (opponentCanMove()){
                    turn = PlayerTurn.BLACK_TURN;
                }else {
                    validMovies++;
                }
                phase = GamePhase.PLAY;
            }
            return true;
        }
        return false;
    }

    public PlayerTurn turnOf(){
        if (validMovies < SIZE){
            if((validMovies/2)%2 == 0){
                return PlayerTurn.BLACK_TURN;
            }
            return PlayerTurn.RED_TURN;
        }
        return validMovies%2 == 0 ? PlayerTurn.BLACK_TURN : PlayerTurn.RED_TURN;
    }



    public boolean isInRange(int rows, int column){
        return isInRange(rows*LENGTH+column);
    }

    public boolean toPoint(int coordinate) {
        if (turnOf()==PlayerTurn.BLACK_TURN && board.get(coordinate)==SquareStatus.BlLACK || turnOf()==PlayerTurn.RED_TURN && board.get(coordinate)==SquareStatus.RED){
            if(winnerChecker == -1 || winnerChecker==coordinate) {
                isPointed = true;
                pointedCoor = coordinate;
                return true;
            }
        }
        pointedCoor = -1;
        return false;
    }

    public boolean isValidMove(int coordinate) {
        if (board.get(coordinate) == SquareStatus.EMPTY){
            if (coordinate == pointedCoor+UP || coordinate == pointedCoor+DOWN || coordinate == pointedCoor+RIGHT || coordinate == pointedCoor+LEFT ){
                board.set(coordinate,board.get(pointedCoor));
                board.set(pointedCoor,SquareStatus.EMPTY);
                return true;
            }
        }
        return false;
    }

    public boolean checkTakes(int coordinate, SquareStatus squareStatus) {
        boolean result = false;
        int squareStatusValue = squareStatus.getNumVal();
        int completedValue = 1 - squareStatusValue;
        if ((coordinate + UP) >= 0 && completedValue == board.get(coordinate + UP).getNumVal()){
            if ((coordinate + 2*UP) >= 0 && squareStatusValue == board.get(coordinate + 2*UP).getNumVal()){
                board.set(coordinate + UP,SquareStatus.EMPTY);
                result = true;
            }
        }
        if ((coordinate + DOWN) < 25 && completedValue == board.get(coordinate + DOWN).getNumVal()){
            if ((coordinate + 2*DOWN) < 25 && squareStatusValue == board.get(coordinate + 2*DOWN).getNumVal()){
                board.set(coordinate + DOWN,SquareStatus.EMPTY);
                result =  true;
            }
        }
        if ((coordinate + RIGHT) < 25 && (coordinate + RIGHT)/LENGTH == coordinate/LENGTH && completedValue == board.get(coordinate + RIGHT).getNumVal()){
            if ((coordinate + 2*RIGHT)/LENGTH == coordinate/LENGTH && squareStatusValue == board.get(coordinate + 2*RIGHT).getNumVal()){
                board.set(coordinate + RIGHT,SquareStatus.EMPTY);
                result =  true;
            }
        }
        if ((coordinate + LEFT) > 0 && (coordinate + LEFT)/LENGTH == coordinate/LENGTH && completedValue == board.get(coordinate + LEFT).getNumVal()){
            if ((coordinate + 2*LEFT)/LENGTH == coordinate/LENGTH && squareStatusValue == board.get(coordinate + 2*LEFT).getNumVal()){
                board.set(coordinate + LEFT,SquareStatus.EMPTY);
                result =  true;
            }
        }
        if (result){
            winnerChecker = coordinate;
        }else {
            winnerChecker = -1;
        }
        return result;
    }

    public boolean isThereWinner() {
        winner = -1;
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i) != SquareStatus.EMPTY){
                if (winner == -1) {
                    winner = board.get(i).getNumVal();
                }else {
                    if (board.get(i).getNumVal() != winner)
                        return false;
                }
            }
        }
        return true;
    }

    public boolean opponentCanMove() {
        
        int opponent = 1 - turn.getNumVal();
        for (int i = 0; i < board.size() ; i++) {
            if (board.get(i).getNumVal() == opponent){
                if ((i+RIGHT)<25 && ((i+RIGHT)/LENGTH == i/LENGTH && board.get(i+RIGHT)== SquareStatus.EMPTY) ||
                ((i+LEFT)>=0 && (i+LEFT)/LENGTH == i/LENGTH && board.get(i+LEFT)== SquareStatus.EMPTY) ||
                        ((i+UP) >= 0&& board.get(i+UP)== SquareStatus.EMPTY )||
                        ((i+DOWN)<25 && board.get(i+DOWN)== SquareStatus.EMPTY)){
                    return true;
                }
            }
        }
        return false;
    }
}
