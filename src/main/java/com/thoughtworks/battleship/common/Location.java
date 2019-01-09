package com.thoughtworks.battleship.common;

public class Location {
    private int row;
    private int col;
    private Location() {}

    public Location(int row, int col) {
        this();
        this.row = row;
        this.col = col;
    }

    public int getCol() { return col; }

    public int getRow() {
        return row;
    }
}
