package com.khaled.seejeh_ancientbeduinchess.java_classes;

import android.content.Context;

import com.khaled.seejeh_ancientbeduinchess.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khaled on 12/18/2016.
 */

public class HumanVsComputer {
    private final PlayerTurn human;
    private final PlayerTurn computer;
    final static int LENGTH = 5;
    public final static int SIZE = LENGTH*LENGTH;
    private final SquareStatus humanStamp;
    private final SquareStatus computerStamp;
    private final Context ctx;
    public List<SquareStatus> board = new ArrayList<>();
    final String COMPUTER = "Computer";
    final String HUMAN = "Human";
    PlayerTurn currentTurn;
    public GamePhase phase;
    int buildingStones = 0;
    public final static int UP = (-1)*LENGTH;
    public final static int DOWN = LENGTH;
    public final static int RIGHT = 1;
    public final static int LEFT = (-1);

    public HumanVsComputer(Context mainActivity, PlayerTurn humanTurn) {
        ctx= mainActivity;
        human = humanTurn;
        humanStamp = humanTurn.getNumVal()== 0 ? SquareStatus.BlLACK : SquareStatus.RED;
        boolean computerBegen = (humanTurn == PlayerTurn.RED_TURN);
        computer = computerBegen ? PlayerTurn.BLACK_TURN : PlayerTurn.RED_TURN;
        computerStamp = computer.getNumVal()== 0 ? SquareStatus.BlLACK : SquareStatus.RED;
        phase = GamePhase.BUILD;
        for (int i = 0; i < SIZE; i++) {
            board.add(null);
            if (i == SIZE/2){
                board.set(SIZE/2,SquareStatus.FORBIDDEN);
            }else {
                board.set(i,SquareStatus.EMPTY);
            }
        }
        if (computerBegen){
            build();
            build();
        }
    }

    private void build() {
        
        if (board.get(0) == SquareStatus.EMPTY){
            board.set(0,computerStamp);
            buildingStones++;
            return;
        }
        if (board.get(LENGTH-1) == SquareStatus.EMPTY){
            board.set(LENGTH-1,computerStamp);
            buildingStones++;
            return;
        }
        if (board.get(SIZE-1) == SquareStatus.EMPTY){
            board.set(SIZE-1,computerStamp);
            buildingStones++;
            return;
        }
        if (board.get(SIZE-LENGTH) == SquareStatus.EMPTY){
            board.set(SIZE-LENGTH,computerStamp);
            buildingStones++;
            return;
        }
        int maxCount = -1;
        int index = -1;
        int emptyIndex = -1;
        for (int i = 1; i < SIZE - 1 ; i++) {
            if (board.get(i) == SquareStatus.EMPTY) {
                emptyIndex = i;
                int counter = 0;
                if (i + UP < 0 || i + DOWN >= SIZE || i % LENGTH == 0 || (i % LENGTH == LENGTH - 1)) {
                    counter++;
                }

                if ((i+UP >=0) && board.get(i+UP)== computerStamp)
                    counter++;
                if (i+DOWN <= SIZE && board.get(i+DOWN)== computerStamp)
                    counter++;
                if (board.get(i+RIGHT)== computerStamp)
                    counter++;
                if (board.get(i+LEFT)== computerStamp)
                    counter++;
                
                if (counter > maxCount){
                    index = i;
                    maxCount =counter;
                }
            }
        }
        if (index >= 0){
            board.set(index,computerStamp);
            buildingStones++;
            return;
        }else if (emptyIndex > 0){
            board.set(index,computerStamp);
            buildingStones++;
            return;
        }
    }

    public boolean build(int coordinate) {
        if (board.get(coordinate) == SquareStatus.EMPTY){
            board.set(coordinate,humanStamp);
            buildingStones++;
            if (buildingStones%2 == 0){
                if (checkPhase(buildingStones)) {
                    playComp();
                    return true;
                }else {
                    build();
                    build();
                }
            }
            return true;
        }
        return false;
    }

    private void playComp() {
    }

    private boolean checkPhase(int buildingStones) {
        if (buildingStones == SIZE-1){
            phase = GamePhase.PLAY;
            return true;
        }
        return false;
    }
}
