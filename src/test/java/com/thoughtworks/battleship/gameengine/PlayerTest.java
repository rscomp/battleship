package com.thoughtworks.battleship.gameengine;

import com.thoughtworks.battleship.common.Location;
import com.thoughtworks.battleship.common.exceptions.InvalidInputException;
import com.thoughtworks.battleship.gameengine.common.enums.FireResult;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class PlayerTest {
    Location loc = mock(Location.class);
    Board bo = mock(Board.class);

    @Test
    public void testNoLastAttackLocWithoutFire(){
        Player p = new Player("dan");
        Assert.assertTrue(p.getName().equals("dan"));
        Assert.assertEquals(null, p.getLastAttackLocation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullBoardCannotBeAssigned() {
        Player p = new Player("ravi");
        Assert.assertTrue(p.getName().equals("ravi"));
        p.setBoard(null);
    }

    @Test
    public void testMissileCanBeAddedAtAValidLocation() {
        Player p = new Player("ajay");
        Assert.assertTrue(p.getName().equals("ajay"));
        p.addMissile(loc);
        Assert.assertFalse(p.noMissileAvailable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnAddingMissileWithNullLocation() {
        Player p = new Player("jazz");
        Assert.assertTrue(p.getName().equals("jazz"));
        p.addMissile(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnFiringAtNullPlayer() {
        Player p = new Player("jazz");
        Assert.assertTrue(p.getName().equals("jazz"));
        try {
            p.fireMissileAt(null);
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testNoAttackPossibleWhenMissilesAreExhauted() {
        Player p = new Player("jazz");
        Assert.assertTrue(p.getName().equals("jazz"));
        try {
            FireResult x = p.fireMissileAt(new Player("raj"));
            Assert.assertEquals(FireResult.NOMISSILE, x);
        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testSuccessfulAttackWithValidData() {
        Player p = new Player("jazz");
        Player o = new Player("raj");
        o.setBoard(bo);
        p.addMissile(loc);
        when(loc.getCol()).thenReturn(1);
        when(loc.getRow()).thenReturn(3);
        try {
            when(bo.bombardAt(loc)).thenReturn(FireResult.HIT);

            FireResult x = p.fireMissileAt(o);
            Location y = p.getLastAttackLocation();

            Assert.assertTrue(p.getName().equals("jazz"));
            Assert.assertTrue(o.getName().equals("raj"));
            Assert.assertEquals(FireResult.HIT, x);
            Assert.assertEquals(1, y.getCol());
            Assert.assertEquals(3, y.getRow());
            verify(loc, times(1)).getRow();
            verify(loc, times(1)).getCol();
            verify(bo, times(1)).bombardAt(loc);

        } catch (InvalidInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testChangeInNameAfterCallingSetName() {
        Player p = new Player("raj");
        p.setName("ravi");
        Assert.assertTrue(p.getName().equals("ravi"));
    }
}
