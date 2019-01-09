package com.thoughtworks.battleship.gameengine.common.validator;

import com.thoughtworks.battleship.gameengine.Board;
import com.thoughtworks.battleship.gameengine.Ship;
import com.thoughtworks.battleship.common.Location;

public class ConstraintValidator {
    public final static short BOARDMINWIDTH = 1;
    public final static short BOARDMAXWIDTH = 9;
    public final static short BOARDMINHEIGHT = 1;
    public final static short BOARDMAXHEIGHT = 26;

    public static void validateBattleFieldSizeConstraints(int height, int width) throws ConstraintViolationException {
        if(height < BOARDMINHEIGHT || height > BOARDMAXHEIGHT || width < BOARDMINWIDTH || width > BOARDMAXWIDTH)
            throw new ConstraintViolationException("battlefield provided height and width are violating system constarints.");
    }

    public static void validateBattleFieldLocationConstraints(Location l, Board b) throws ConstraintViolationException {
        if(l == null || b == null)
            throw new IllegalArgumentException("Illegal: Board and Location details must be present");

        if(l.getCol() < BOARDMINWIDTH || l.getCol() > b.getWidth() || l.getRow() < BOARDMINHEIGHT || l.getRow() > b.getHeight())
            throw new ConstraintViolationException("provided location does not exist on battlefield");
    }

    public static void validateShipPlacementLocationWithInBoard(Board b, Location l, Ship s) throws ConstraintViolationException {
        if(s == null)
            throw new IllegalArgumentException("Illegal: Ship details are must.");
        validateBattleFieldLocationConstraints(l, b);
        validateBattleFieldLocationConstraints(new Location(l.getRow(), l.getCol() + s.getWidth() - 1), b);
        validateBattleFieldLocationConstraints(new Location(l.getRow() + s.getHeight() - 1, l.getCol()), b);
        validateBattleFieldLocationConstraints(new Location(l.getRow() + s.getHeight() - 1, l.getCol() + s.getWidth() - 1), b);
    }
}
