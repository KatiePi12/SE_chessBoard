package com.company;

public class PlacesUtil {
    int boardSize;

    public PlacesUtil(int boardSize) {
        this.boardSize = boardSize;
    }

    public String getNormalizedHorizontal(int horizontal) {
        return String.valueOf((boardSize - 1 - horizontal));
    }
}
