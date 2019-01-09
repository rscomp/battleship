package com.thoughtworks.battleship.gameengine;

import com.thoughtworks.battleship.common.Location;
import com.thoughtworks.battleship.common.enums.PlayerCode;
import com.thoughtworks.battleship.common.enums.ShipType;
import com.thoughtworks.battleship.common.exceptions.InvalidInputException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;




public class BattleShipGameTest {
    Location loc = mock(Location.class);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test(expected = IllegalStateException.class)
    public void testGameCanNotBeReInitialized(){
        BattleShipGame g = new BattleShipGame("Ravi", "Suraj");
        try {
            g.initBattleField(2, 5);
            g.initBattleField(5, 6);
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testExceptionWhenIntitializingGameBeyondConstraints(){
        BattleShipGame g = new BattleShipGame("Ravi", "Suraj");
        try {
            g.initBattleField(50, 5);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof InvalidInputException);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testGameCanNotBeStartedBeforeIntialization() {
        BattleShipGame g = new BattleShipGame("Ravi", "Suraj");
        g.playGame();
    }

    @Test(expected = IllegalStateException.class)
    public void testGameCanNotBeInitializedAfterStart() {
        BattleShipGame g = new BattleShipGame("Ravi", "Suraj");
        try{
            g.initBattleField(1, 2);
        } catch (InvalidInputException e) {
            Assert.fail();
        }
        g.playGame();
        try{
            g.initBattleField(4, 2);
            Assert.fail();
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testShipCanNotBePlacedWhenGameIsNotInitialized() {
        BattleShipGame g = new BattleShipGame("Ravi", "Suraj");
        try {
            g.placeShip(PlayerCode.ONE, ShipType.Q, 1, 2, loc);
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testShipCanNotBePlacedWhenGameIsStarted() {
        BattleShipGame g = new BattleShipGame("Ravi", "Suraj");
        try {
            g.initBattleField(9, 5);
            g.playGame();
            g.placeShip(PlayerCode.TWO, ShipType.P, 4, 7, loc);
            Assert.fail();
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShipCanNotBePlacedWithoutShipType() {
        BattleShipGame g = new BattleShipGame("Ravi", "Suraj");
        try {
            g.initBattleField(9, 5);
            g.placeShip(PlayerCode.TWO, null, 4, 7, loc);
            Assert.fail();
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShipCanNotBePlacedWithoutPlayerCode() {
        BattleShipGame g = new BattleShipGame("Ravi", "Suraj");
        try {
            g.initBattleField(9, 5);
            g.placeShip(null, ShipType.P, 4, 7, loc);
            Assert.fail();
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testShipCanBePlacedWithValidData() {
        BattleShipGame g = new BattleShipGame("Ravi", "Suraj");
        when(loc.getCol()).thenReturn(1);
        when(loc.getRow()).thenReturn(2);
        try {
            g.initBattleField(9, 5);
            g.placeShip(PlayerCode.ONE, ShipType.P, 4, 2, loc);
            verify(loc, atLeast(1)).getRow();
            verify(loc, atLeast(1)).getCol();
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test(expected = IllegalStateException.class)
    public void missileCanNotBeAddedToPlayerWhenGameStarted() {
        BattleShipGame g = new BattleShipGame("Ravi", "Suraj");
        try {
            g.initBattleField(9, 5);
            g.playGame();
            g.addMissile(PlayerCode.TWO, loc);
            Assert.fail();
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testMissileCanBeAddedToPlayerWithValidData() {
        BattleShipGame g = new BattleShipGame("Ravi", "Suraj");
        try {
            g.initBattleField(9, 5);
            g.addMissile(PlayerCode.ONE, loc);
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testGamePlayWithValidSetup() {
        BattleShipGame g = new BattleShipGame("Ravi", "Suraj");
        when(loc.getRow()).thenReturn(1);
        when(loc.getCol()).thenReturn(2);
        try {
            g.initBattleField(9, 5);
            g.placeShip(PlayerCode.ONE, ShipType.P, 1, 2, loc);
            g.placeShip(PlayerCode.TWO, ShipType.P, 2, 2, loc);
            g.addMissile(PlayerCode.ONE, loc);
            Assert.assertTrue(outContent.toString().contains("Missile added for Ravi"));
            when(loc.getRow()).thenReturn(9);
            g.addMissile(PlayerCode.ONE, loc);
            Assert.assertTrue(outContent.toString().contains("Missile added for Ravi"));
            when(loc.getRow()).thenReturn(5);
            g.addMissile(PlayerCode.TWO, loc);
            Assert.assertTrue(outContent.toString().contains("Missile added for Suraj"));
            when(loc.getRow()).thenReturn(2);
            g.addMissile(PlayerCode.TWO, loc);
            Assert.assertTrue(outContent.toString().contains("Missile added for Suraj"));
            g.playGame();
            verify(loc, atLeast(1)).getCol();
            verify(loc, atLeast(1)).getRow();
            Assert.assertTrue(outContent.toString().contains("GAME OVER"));
        } catch (InvalidInputException e) {
            Assert.fail();
        }

    }



}
