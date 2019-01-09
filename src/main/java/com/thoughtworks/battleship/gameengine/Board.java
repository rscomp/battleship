package com.thoughtworks.battleship.gameengine;

import com.thoughtworks.battleship.common.enums.ShipType;
import com.thoughtworks.battleship.common.exceptions.InvalidInputException;
import com.thoughtworks.battleship.common.Location;
import com.thoughtworks.battleship.gameengine.common.FieldCell;
import com.thoughtworks.battleship.gameengine.common.enums.FireResult;
import com.thoughtworks.battleship.gameengine.common.validator.ConstraintValidator;

public class Board {
    private FieldCell[][] battleField;
    private int width;
    private int height;
    private int aliveShipCount;

    private Board() {
        aliveShipCount = 0;
    }

    /**
     * Constructor to initialize battlefield with given height and width
     * @param height
     * @param width
     */
    public Board(int height, int width) {
        this();
        if(height < 1 || width < 1)
            throw new IllegalArgumentException("Illegal BattleField (Height, Width): ("+
                    height + ", " + width + ")");
        this.height = height;
        this.width = width;
        battleField = new FieldCell[height][width];
    }

    private boolean isShipOverlaping(Location l, Ship s) {
        for(int col = l.getCol(); col < l.getCol() + s.getWidth(); col++) {
            for (int row = l.getRow(); row < l.getRow() + s.getHeight(); row++) {
                FieldCell targetCell = battleField[row - 1][col - 1];
                if (targetCell != null && targetCell.getShip() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * function to place ship over battlefield
     * @param l
     * @param s
     * @throws InvalidInputException when given ship cannot be placed over board at given location
     */
    public void placeShip(Location l, Ship s) throws InvalidInputException {
        if(l == null || s == null)
            throw new IllegalArgumentException("Illegal: location and ship can not be null.");
        ConstraintValidator.validateShipPlacementLocationWithInBoard(this, l, s);

        boolean overlapFlag = isShipOverlaping(l, s);
        if(overlapFlag)
            throw new InvalidInputException("Illegal: Ship can not be placed at given location");
        else {
            int cellCapacity = 1;
            if(s.getType() == ShipType.Q)
                cellCapacity = 2;
            for(int col = l.getCol(); col < l.getCol() + s.getWidth(); col++) {
                for (int row = l.getRow(); row < l.getRow() + s.getHeight(); row++) {
                    battleField[row - 1][col - 1] = new FieldCell(s, cellCapacity);
                }
            }
            aliveShipCount++;
        }
    }

    /**
     * function to drop a bomb over board
     * @param l
     * @return FireResult of bombing
     * @throws InvalidInputException when location of missile is out of board
     */
    public FireResult bombardAt(Location l) throws InvalidInputException {
        ConstraintValidator.validateBattleFieldLocationConstraints(l, this);
        FieldCell targetCell = battleField[l.getRow() - 1][l.getCol() - 1];

        if(targetCell == null)
            targetCell = new FieldCell(null, 0);

        if(targetCell.getCapacity() == 0)
            return FireResult.MISS;

        Ship s = targetCell.getShip();

        FireResult r = s.takeMissileHit();
        if(r == FireResult.SINK)
            aliveShipCount--;
        targetCell.decreseCapacity();
        return FireResult.HIT;
    }

    /**
     * getter for no of alive ships on the board
     * @return no of ships alive on board
     */
    public int getAliveShipCount() {
        return aliveShipCount;
    }

    /**
     * getter for width of battlefield
     * @return width of board
     */
    public int getWidth() {
        return width;
    }

    /**
     * getter for height of battlefield
     * @return height of board
     */
    public int getHeight() {
        return height;
    }
}
