package com.company;

public class Place {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private PlacesUtil placesUtil;

    public Place(Place place) {
        this.isFree = place.isFree();
        this.isAttacked = place.isAttacked();
        this.horizontal = place.getHorizontal();
        this.vertical = place.getVertical();
        this.placesUtil = place.getPlacesUtil();
    }

    public int getVertical() {
        return vertical;
    }

    public PlacesUtil getPlacesUtil() {
        return placesUtil;
    }

    public int getHorizontal() {
        return horizontal;
    }

    enum Vertical{
        A, B, C, D, E, F, G, H, I ,J, K;

        private final int value;

        Vertical() {
            this.value = ordinal();
        }

        public static Vertical fromValue(int value)
                throws IllegalArgumentException {
            try {
                return Vertical.values()[value];
            } catch(ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Unknown enum value :"+ value);
            }
        }
    }

    private int vertical;
    private int horizontal;
    private boolean isFree;
    private boolean isAttacked;

    public Place(int horizontal, int vertical, PlacesUtil placesUtil) {
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.isFree = true;
        this.isAttacked = false;
        this.placesUtil = placesUtil;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean isAttacked() {
        return isAttacked;
    }

    public void setAttacked(boolean attacked) {
        isAttacked = attacked;
    }

    public String getVerticalName() {
        return Vertical.fromValue(this.vertical).toString();
    }

    public void print() {
        if(!isFree) {
            System.out.print(ANSI_RED + getVerticalName() + placesUtil.getNormalizedHorizontal(horizontal) + ANSI_RESET);
        } else if (isAttacked) {
            System.out.print(ANSI_YELLOW + getVerticalName() + placesUtil.getNormalizedHorizontal(horizontal) + ANSI_RESET);
        } else {
            System.out.print(getVerticalName() + placesUtil.getNormalizedHorizontal(horizontal));
        }
    }
}
