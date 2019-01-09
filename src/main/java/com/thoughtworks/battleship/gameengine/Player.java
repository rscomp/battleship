package com.thoughtworks.battleship.gameengine;

import com.thoughtworks.battleship.common.exceptions.InvalidInputException;
import com.thoughtworks.battleship.common.Location;
import com.thoughtworks.battleship.gameengine.common.enums.FireResult;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private Board board;
    private int nextAvailableMissile;
    private List<Location> missiles = new ArrayList<>();

    private Player() {}

    /**
     * Constructor to initialize Player object with name
     * @param n
     */
    public Player(String n) {
        this();
        name = n;
    }

    /**
     * function for player to fire missile at opponent
     * @param p
     * @return FireResult
     * @throws InvalidInputException when missile is fired out of board
     * @throws NullPointerException when player does not have board
     * @throws IllegalArgumentException when @param is null
     */
    public FireResult fireMissileAt(Player p) throws InvalidInputException, NullPointerException {
        if(p == null)
            throw new IllegalArgumentException("Illegal: Opponent details are not provided.");
        if(noMissileAvailable())
            return FireResult.NOMISSILE;
        return p.getBoard().bombardAt(missiles.get(nextAvailableMissile++));
    }

    /**
     * get battlefield assigned to player
     * @return Board of Player
     */
    public Board getBoard() { return  board; }

    /**
     * set battlefield for a Player
     * @param b
     * @throws IllegalArgumentException when @param is null
     */
    public void setBoard(Board b) {
        if(b == null)
            throw new IllegalArgumentException("Illegal: Board can not be null");
        this.board = b;
    }

    /**
     * set name of player to n
     * @param n name of the player
     */
    public void setName(String n) {
        name = n;
    }

    /**
     * get name of the player
     * @return name of the Player
     */
    public String getName() {
        return name;
    }

    /**
     * function to provide missile to a Player
     * @param l defining target of the missile
     * @throws IllegalArgumentException when @param is null
     */
    public void addMissile(Location l) {
        if(l == null)
            throw new IllegalArgumentException("Illegal: missile location can not be null.");
        missiles.add(l);
    }

    /**
     * function that get last attacking location by the player
     * @return null is no missile is fired else location of last missile target
     */
    public Location getLastAttackLocation() {
        if(nextAvailableMissile == 0)
            return null;
        else
            return missiles.get(nextAvailableMissile - 1);
    }

    /**
     * function to check whether Player has alive missile
     * @return true when missile available
     */
    public boolean noMissileAvailable() {
        return nextAvailableMissile == missiles.size();
    }

}
