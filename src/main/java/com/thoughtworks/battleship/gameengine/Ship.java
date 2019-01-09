package com.thoughtworks.battleship.gameengine;

import com.thoughtworks.battleship.common.enums.ShipType;
import com.thoughtworks.battleship.gameengine.common.enums.FireResult;

public class Ship {
    private ShipType type;
    private int height;
    private int width;
    private int remainingCapacity;

    private Ship() {}

    private Ship(int height, int width) {
        this();
        if(height < 1 || width < 1)
            throw new IllegalArgumentException("Illegal Ship (Height, Width): ("+
                    height + ", " + width + ")");
        this.height = height;
        this.width = width;
        remainingCapacity = height * width;
    }

    /**
     * Ship constructor that takes height, width and ShipType
     * @param height int
     * @param width int
     * @param t ShipType.class
     */
    public Ship(int height, int width, ShipType t) {
        this(height, width);
        if(t == null)
            throw new IllegalArgumentException("Illegal ShipType: "+ t);
        type = t;
        int multiplyer = 1;
        if(type == ShipType.Q)
            multiplyer = 2;
        remainingCapacity *= multiplyer;
    }

    /**
     * function that describes ship getting hit by missile
     * @return FireResult HIT or SINK based on capacity
     * @throws IllegalStateException when ship capacity is zero
     */
    public FireResult takeMissileHit() {
        if(getRemainingCapacity() == 0)
            throw new IllegalStateException("Illegal state: Ship already sunk");
        remainingCapacity--;
        return getRemainingCapacity() == 0? FireResult.SINK : FireResult.HIT;
    }

    /**
     * getter function for ship remainingCapacity
     * @return int value of ship remainingCapacity
     */
    public int getRemainingCapacity() { return remainingCapacity; }

    /**
     * getter function for ship type
     * @return ShipType.class
     */
    public ShipType getType() { return type; }

    /**
     * getter function for ship height
     * @return int value of ship height
     */
    public int getHeight() { return height; }

    /**
     * getter function for ship width
     * @return int value of ship width
     */
    public int getWidth() { return width; }
}
