package com.company;

import java.util.HashMap;
import java.util.Map;

public class BoardRememberer {

    Map<Integer, Board> boardVerticalMapping = new HashMap<>();
    Map<Integer, Integer> verticalBestSkipMapping = new HashMap<>();
    int boardSize;

    public BoardRememberer(Board currentBoard) {
        Board newBoard = new Board(currentBoard);
        boardVerticalMapping.put(0, newBoard);
        verticalBestSkipMapping.put(0, 0);
        boardSize = currentBoard.getSize();
    }

    public void rememberBoard(int vertical, Board board) {
        Board newBoard = new Board(board);
        boardVerticalMapping.put(vertical + 1, newBoard);
        verticalBestSkipMapping.put(vertical + 1, 0);
    }

    public Board getSpecificBoard(int vertical) {
        return boardVerticalMapping.get(vertical);
    }

    public int getSpecificBestsSkip(int vertical) {
        return verticalBestSkipMapping.get(vertical);
    }

    public void  increaseTheBestSkip(int vertical) {
        if ( vertical >= 0 ) {
            int currentBestSkip = verticalBestSkipMapping.get(vertical);
            verticalBestSkipMapping.put(vertical, ++currentBestSkip);
            resetHighestVerticals(vertical);
        }
    }

    private void resetHighestVerticals(int vertical) {
        for (int i = vertical + 1; i < boardSize; i++) {
            verticalBestSkipMapping.put(i, 0);
        }
    }
}
