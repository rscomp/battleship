package com.thoughtworks.battleship.gameengine;

import com.thoughtworks.battleship.common.FormatUtil;
import com.thoughtworks.battleship.common.enums.PlayerCode;
import com.thoughtworks.battleship.common.enums.ShipType;
import com.thoughtworks.battleship.common.exceptions.InvalidInputException;
import com.thoughtworks.battleship.common.Location;
import com.thoughtworks.battleship.gameengine.common.enums.FireResult;
import com.thoughtworks.battleship.gameengine.common.validator.ConstraintValidator;

public class BattleShipGame {
    private Player firstPlayer;
    private Player secondPlayer;
    private boolean isGameStarted;
    private boolean isGameInitialized;


    public BattleShipGame() {
        firstPlayer = new Player("Player-1");
        secondPlayer = new Player("Player-2");
        isGameStarted = false;
        isGameInitialized = false;
    }

    public BattleShipGame(String firstPlName, String secondPlName) {
        this();
        firstPlayer.setName(firstPlName);
        secondPlayer.setName(secondPlName);
    }

    /**
     * function to initialize battlefield for both players
     * @param height
     * @param width
     * @throws InvalidInputException when given @param violates system constraints
     */
    public void initBattleField(int height, int width) throws InvalidInputException {
        if(isGameInitialized)
            throw new IllegalStateException("Illegal state: game already intialized");
        ConstraintValidator.validateBattleFieldSizeConstraints(height, width);
        firstPlayer.setBoard(new Board(height, width));
        secondPlayer.setBoard(new Board(height, width));
        isGameInitialized = true;
    }

    /**
     * function to place ship on a player board based on playerCode, Ship and location details
     * @param playerCode
     * @param shipType
     * @param height
     * @param width
     * @param l
     * @throws InvalidInputException when ship can not be placed on board with given location and ship details
     */
    public void placeShip(PlayerCode playerCode, ShipType shipType, int height, int width, Location l) throws InvalidInputException {
        if(isGameStarted)
            throw new IllegalStateException("Illegal state: Ship can not be placed after start of the game.");
        if(!isGameInitialized)
            throw new IllegalStateException("Illegal state: game is not initialized yet");
        if(shipType == null)
            throw new IllegalArgumentException("Illegal: ShipType can not be null.");

        Player p = selectPalyer(playerCode);
        p.getBoard().placeShip(l, new Ship(height, width, shipType));
    }

    /**
     * function to assign missile to a Player based on playerCode
     * @param playerCode
     * @param l
     * @throws IllegalStateException when game is already started
     * @throws IllegalArgumentException when null playerCode is provided
     */
    public void addMissile(PlayerCode playerCode, Location l) {
        if(isGameStarted)
            throw new IllegalStateException("Illegal state: Missile can not be added after start of the game.");
        Player p = selectPalyer(playerCode);
        p.addMissile(l);
        System.out.println("Missile added for " + p.getName());
    }

    /**
     * function to start playing baatlefield game
     * @throws IllegalStateException when game is not initialized
     */
    public void playGame() {
        if(!isGameInitialized)
            throw new IllegalStateException("Illegal state: game is not initialized yet");
        isGameStarted = true;
        Player attacker = firstPlayer;
        Player defender = secondPlayer;
        FireResult r;
        while(attacker.getBoard().getAliveShipCount() > 0 && defender.getBoard().getAliveShipCount() > 0 && (!attacker.noMissileAvailable() || !defender.noMissileAvailable())) {
            try {
                r = attacker.fireMissileAt(defender);
                if(r == FireResult.MISS || r == FireResult.NOMISSILE) {
                    if(r == FireResult.MISS)
                        System.out.println(attacker.getName() + " fires a missile with target " + FormatUtil.LocToStr(attacker.getLastAttackLocation()) + " which got miss");
                    else if(r == FireResult.NOMISSILE)
                        System.out.println(attacker.getName() + " has no more missiles left to launch");

                    Player p = attacker;
                    attacker = defender;
                    defender = p;
                }
                else {
                   System.out.println(attacker.getName() + " fires a missile with target " + FormatUtil.LocToStr(attacker.getLastAttackLocation()) + " which got hit");
                   if(defender.getBoard().getAliveShipCount() == 0) {
                       System.out.println(attacker.getName() + " won the battle");
                       break;
                   }
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("+++++++ GAME OVER +++++++++++");
    }

    private Player selectPalyer(PlayerCode playerCode) {
        Player p = null;
        if( playerCode == null )
            throw new IllegalArgumentException("Illegal: PlayerCode can not be null.");
        switch(playerCode) {
            case ONE:
                p = firstPlayer;
                break;
            case TWO:
                p = secondPlayer;
                break;
        }
        return p;
    }


}
