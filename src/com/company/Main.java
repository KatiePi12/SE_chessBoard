package com.company;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(4);
        HeuristicAlgorythm heuristicAlgorythm = new HeuristicAlgorythm(board);
        heuristicAlgorythm.runAlgorythm();
    }
}
