package com.company;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class HeuristicAlgorythm {

    private Board board;

    public HeuristicAlgorythm(Board board) {
        this.board = board;
    }

    public List<Place> getPlacesForVertical(int vertical, Board currentBoard) {
        Place[][] places = currentBoard.getPlaces();
        int size = currentBoard.getSize();
        List<Place> verticalPlaces = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Place place = places[i][vertical];
            verticalPlaces.add(place);
        }
        return verticalPlaces;
    }

    public void runAlgorythm() {
        int boardSize = board.getSize();
        BoardRememberer boardRememberer = new BoardRememberer(board);
        Board currentBoard;
        int skipBests;

        for (int vertical = 0; vertical < boardSize; vertical++) {
            skipBests = boardRememberer.getSpecificBestsSkip(vertical);
            currentBoard = boardRememberer.getSpecificBoard(vertical);
            List<Place> verticalPlaces = getPlacesForVertical(vertical, currentBoard);
            Board newBoard = putHetmanInTheBestPlace(verticalPlaces, currentBoard, skipBests);
            boardRememberer.increaseTheBestSkip(vertical);
            if (newBoard != null) {
                boardRememberer.rememberBoard(vertical, newBoard);
            } else {
                vertical = vertical - 2;
            }
        }
    }

    private Board putHetmanInTheBestPlace(List<Place> avaliablePlaces, Board currentBoard,  int skipBests) {
        Map<Place, Integer> placeRating = new HashMap<>();
        avaliablePlaces.stream().forEach(place -> placeRating.put(place, calculateRating(place, currentBoard)) );
        Map<Place,Integer> sorted = placeRating.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        Place theBestPlace = getPlaceWithTheHighestFunctionValue(sorted, skipBests);
        Board newBoard = new Board(currentBoard);
        if (theBestPlace != null) {
            newBoard.setHetman(theBestPlace);
            newBoard.printBoard();
        } else {
            newBoard.printBoard();
            newBoard = null;
        }
        return newBoard;
    }

    private Place getPlaceWithTheHighestFunctionValue(Map<Place,Integer> placeRating, int skipBests) {
        removeBests(placeRating, skipBests);
        if (placeRating.size() == 0)
            return null;
        Entry<Place, Integer> theBestRatingPlace = iterateOverMap(placeRating);
        int theHighestRateValue = theBestRatingPlace.getValue();

        for (Map.Entry<Place, Integer> placeRate : placeRating.entrySet()) {
            if (placeRate != null) {
                if (placeRate.getValue() >= theHighestRateValue) {
                    theHighestRateValue = placeRate.getValue();
                    theBestRatingPlace = placeRate;
                }
            }
        }

        if (theBestRatingPlace.getValue() == 0) {
            System.out.println("All remained functions values are 0");
            return null;
        }

        Place theBestPlace = theBestRatingPlace.getKey();

        System.out.print("Found place for vertical " + theBestPlace.getVerticalName() + " with the " +
                "highest function value " + theBestRatingPlace.getValue() + " put on " );
        theBestPlace.print();
        System.out.println();

        return theBestPlace;
    }

    private void removeBests(Map<Place,Integer> placeRating, int bestToRemoveAmount) {
        List<Place> tempList = new ArrayList<>(placeRating.keySet());

        for( int i = 0; i < bestToRemoveAmount; i++) {
            placeRating.remove(tempList.get(tempList.size()-1));
            tempList.remove(tempList.size()-1);
        }
    }

    private Integer calculateRating(Place place, Board currentBoard) {
        if (place.isAttacked())
            return 0;
        Board tempBoard = new Board(currentBoard);
        Place tmpPlace = tempBoard.getPlace(place.getHorizontal(), place.getVertical());
        tempBoard.setHetman(tmpPlace);
        //plus one because we have one place free where we want to put hetman
        return tempBoard.getNotAttackedAmount() + 1;
    }

    Entry<Place, Integer>iterateOverMap(Map<Place, Integer> placeRating) {
        return placeRating.entrySet().iterator().next();
    }
}
