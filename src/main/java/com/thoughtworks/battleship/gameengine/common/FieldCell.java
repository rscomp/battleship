package com.thoughtworks.battleship.gameengine.common;

import com.thoughtworks.battleship.gameengine.Ship;

/**
 * class that describes unit of battlefield
 */
public class FieldCell {
    private int capacity;
    private Ship ship;

    private FieldCell() { }

    public FieldCell(Ship s, int c) {
        this();
        ship = s;
        capacity =c;
    }

    public int getCapacity() {
        return capacity;
    }

    public Ship getShip() {
        return ship;
    }

    public void decreseCapacity() {
        if(getCapacity() == 0)
            throw new IllegalStateException("Illegal state: shell no more has capacity");
        capacity--;
    }
}
