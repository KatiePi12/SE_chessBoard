package com.company;

public class Board {
    private Place places[][];
    private int size;
    private int hetmanToBePlaced;

    public Board(int size) {
        this.size = size;
        hetmanToBePlaced = size;
        places = initializePlaces();
    }

    public Board(Board board) {
        size = board.getSize();
        hetmanToBePlaced = size;
        places = initializePlaces(board.getPlaces());
    }

    private Place [][] initializePlaces() {
        Place [][] tempPlaces = new Place[size][size];
        for ( int i = 0; i < size; i++) {
            for ( int j = 0; j < size; j++) {
                tempPlaces[i][j] = new Place(i, j, new PlacesUtil(size));
            }
        }
        return tempPlaces;
    }

    private Place [][] initializePlaces(Place [][] places) {
        Place [][] tempPlaces = new Place[size][size];
        for ( int i = 0; i < size; i++) {
            for ( int j = 0; j < size; j++) {
                tempPlaces[i][j] = new Place(places[i][j]);
            }
        }
        return tempPlaces;
    }

    public void printBoard() {
        for ( int i = 0; i < size; i++) {
            for ( int j = 0; j < size; j++) {
                System.out.print(" ");
                places[i][j].print();
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getNotAttackedAmount() {
        int notAttackedAmount = 0;
        for ( int i = 0; i < size; i++) {
            for ( int j = 0; j < size; j++) {
                if (!places[i][j].isAttacked() && places[i][j].isFree())
                    notAttackedAmount++;
            }
        }
        return notAttackedAmount;
    }

    public int getHetmanToBePlaced() {
        return hetmanToBePlaced;
    }

    public void substractHetmanToBePlaced() {
        hetmanToBePlaced--;
    }

    public Place[][] getPlaces() {
        return places;
    }

    public int getSize() {
        return size;
    }

    public void setHetman(Place place) {
        Place searchedPlace = getPlace(place.getHorizontal(),place.getVertical());
        searchedPlace.setFree(false);
        setAttackedPlaces(searchedPlace);
    }

    private void attackVertical(Place place) {
        int vertical = place.getVertical();
        for(int i = 0; i < size; i++) {
            places[i][vertical].setAttacked(true);
        }
    }

    private void attackHorizontal(Place place) {
        int horizontal = place.getHorizontal();
        for (int i = 0; i < size; i++) {
            places[horizontal][i].setAttacked(true);
        }
    }

    private void attackDiagonals(Place place) {
        attackUpperDiagonal(place);
        attackLowerDiagonal(place);
    }

    private void attackUpperDiagonal(Place place) {
        int vertical = place.getVertical();
        int horizontal = place.getHorizontal();
        while(vertical-1 >= 0 && horizontal+1 < size) {
            vertical--;
            horizontal++;
            places[horizontal][vertical].setAttacked(true);
        }

        while(vertical+1 < size && horizontal-1 >= 0) {
            vertical++;
            horizontal--;
            places[horizontal][vertical].setAttacked(true);
        }
    }

    private void attackLowerDiagonal(Place place) {
        int vertical = place.getVertical();
        int horizontal = place.getHorizontal();
        while(vertical-1 >= 0 && horizontal-1 >= 0) {
            vertical--;
            horizontal--;
            places[horizontal][vertical].setAttacked(true);
        }

        while(vertical+1 < size && horizontal+1 < size) {
            vertical++;
            horizontal++;
            places[horizontal][vertical].setAttacked(true);
        }
    }

    private void setAttackedPlaces(Place place) {
        attackVertical(place);
        attackHorizontal(place);
        attackDiagonals(place);
    }

    public Place getPlace(int horizontal, int vertical) {
        return places[horizontal][vertical];
    }
}
