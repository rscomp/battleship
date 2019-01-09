package com.thoughtworks.battleship.gameengine;

import com.thoughtworks.battleship.common.enums.ShipType;
import com.thoughtworks.battleship.gameengine.common.enums.FireResult;
import org.junit.Assert;
import org.junit.Test;

public class ShipTest {

    @Test
    public void testShipSunkAfterCapacityExhausted() {
        Ship s = new Ship(1,1, ShipType.P);
        Assert.assertEquals(FireResult.SINK, s.takeMissileHit());
    }

    @Test(expected = IllegalStateException.class)
    public void testShipCanNotResink() {
        Ship s = new Ship(1,1, ShipType.Q);
        s.takeMissileHit();
        s.takeMissileHit();
        s.takeMissileHit();
    }

    @Test
    public void testShipCapacityDecreasesOnHit() {
        Ship s = new Ship(1,1, ShipType.P);
        int c = s.getRemainingCapacity();
        s.takeMissileHit();
        Assert.assertEquals(c - 1, s.getRemainingCapacity());
    }

    @Test
    public void testShipCreationWithValidTypeHeightWidth() {
        Ship s = new Ship(2, 4, ShipType.Q);
        Assert.assertEquals(ShipType.Q, s.getType());
        Assert.assertEquals(2, s.getHeight());
        Assert.assertEquals(4, s.getWidth());
        Assert.assertEquals(2 * 4 * 2, s.getRemainingCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShipCanNotBeCreatedWithNegativeHeightOrWidth() {
        Ship s = new Ship(-2, 4, ShipType.P);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShipCanNotSetWithNullType() {
        Ship s = new Ship(2, 4, null);
    }
}
