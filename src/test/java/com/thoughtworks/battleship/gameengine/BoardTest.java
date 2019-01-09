package com.thoughtworks.battleship.gameengine;

import com.thoughtworks.battleship.common.Location;
import com.thoughtworks.battleship.common.enums.ShipType;
import com.thoughtworks.battleship.common.exceptions.InvalidInputException;
import com.thoughtworks.battleship.gameengine.common.enums.FireResult;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BoardTest {
    Location loc = mock(Location.class);
    Ship sh = mock(Ship.class);

    @Test(expected = IllegalArgumentException.class)
    public void testBoardCannotBeCreatedWithInvalidHeightAndWidth(){
        Board b = new Board(-1, 2);
    }

    @Test
    public void testBoardCanBeCreatedWithValidHeightAndWidth() {
        Board b = new Board(1, 2);
        Assert.assertEquals(1, b.getHeight());
        Assert.assertEquals(2, b.getWidth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBoardCannotBeCreatedWithHeightOrWidthLessThan1() {
        Board b = new Board(-1, 2);
        Board c = new Board(1, 0);
    }

    @Test
    public void testCanNotPlaceShipOutsideOrIntersectingBoardBoundary() {
        Board b = new Board(5,3);
        when(loc.getRow()).thenReturn(3);
        when(loc.getCol()).thenReturn(2);
        when(sh.getHeight()).thenReturn(2);
        when(sh.getWidth()).thenReturn(5);
        try {
            b.placeShip(loc, sh);
            Assert.fail();
        } catch (InvalidInputException e) {
            verify(loc, atLeast(1)).getCol();
            verify(loc, atLeast(1)).getRow();
            verify(sh, atLeast(1)).getWidth();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShipCanNotBePlacedWithNullLocation(){
        Board b = new Board(1, 2);
        try {
            b.placeShip(null, sh);
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShipCanNotPlaceNullShipOnBoard(){
        Board b = new Board(1, 2);
        try {
            b.placeShip(loc, null);
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testShipCanNotBeOverlapedWithAnotherShipOnBoard(){
        Board b = new Board(7, 9);
        Ship sh2 = mock(Ship.class);
        Location loc2 = mock(Location.class);

        when(loc.getCol()).thenReturn(2);
        when(loc.getRow()).thenReturn(5);
        when(sh.getHeight()).thenReturn(2);
        when(sh.getWidth()).thenReturn(3);
        when(sh.getType()).thenReturn(ShipType.Q);

        when(loc2.getCol()).thenReturn(1);
        when(loc2.getRow()).thenReturn(3);
        when(sh2.getHeight()).thenReturn(3);
        when(sh2.getWidth()).thenReturn(3);

        try {
            b.placeShip(loc, sh);
            b.placeShip(loc2, sh2);
            Assert.fail();
        } catch (InvalidInputException e) {
            verify(loc, atLeast(1)).getCol();
            verify(loc, atLeast(1)).getRow();
            verify(sh, atLeast(1)).getWidth();
            verify(sh, atLeast(1)).getHeight();
            verify(sh, times(1)).getType();
            verify(loc2, atLeast(1)).getCol();
            verify(loc2, atLeast(1)).getRow();
            verify(sh2, atLeast(1)).getWidth();
            verify(sh2, atLeast(1)).getHeight();
            Assert.assertEquals(1, b.getAliveShipCount());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionBoardBombardAtNullLoc() {
        Board b = new Board(5, 7);
        try {
            b.bombardAt(null);
            Assert.fail();
        } catch(InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testFireResultMissWhenBombardingAtWaterField() {
        Board b = new Board(3, 4);
        when(loc.getCol()).thenReturn(1);
        when(loc.getRow()).thenReturn(2);
        try {
            Assert.assertEquals(FireResult.MISS, b.bombardAt(loc));
            verify(loc, atLeast(1)).getRow();
            verify(loc, atLeast(1)).getCol();
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testFireResultHitWhenBombardingAtShipCell() {
        Board b = new Board(3, 4);

        when(sh.takeMissileHit()).thenReturn(FireResult.SINK);
        when(loc.getRow()).thenReturn(2);
        when(loc.getCol()).thenReturn(1);
        when(sh.getWidth()).thenReturn(1);
        when(sh.getHeight()).thenReturn(1);
        when(sh.getType()).thenReturn(ShipType.P);

        try {
            b.placeShip(loc, sh);
            Assert.assertEquals(FireResult.HIT, b.bombardAt(loc));
            verify(loc, atLeast(1)).getCol();
            verify(loc, atLeast(1)).getRow();
            verify(sh, times(1)).takeMissileHit();
            verify(sh, atLeast(1)).getWidth();
            verify(sh, atLeast(1)).getHeight();
            verify(sh, atLeast(1)).getType();
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

}
