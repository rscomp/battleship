package com.thoughtworks.battleship.gameengine.common;

import com.thoughtworks.battleship.gameengine.Ship;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class FieldCellTest {
    Ship sh = mock(Ship.class);

    @Test(expected = IllegalStateException.class)
    public void testCannotDecreaseCapacityAfterZero() {
        FieldCell f = new FieldCell(sh, 2);
        f.decreseCapacity();
        f.decreseCapacity();
        f.decreseCapacity();
    }

    @Test
    public void testChangeInCapacityAfterDecreasing() {
        FieldCell f = new FieldCell(null, 2);
        int c = f.getCapacity();
        f.decreseCapacity();
        Assert.assertEquals(c - 1, f.getCapacity());
        Assert.assertEquals(null, f.getShip());
    }

    @Test
    public void testCreateFieldCellWithValidShip() {
        FieldCell f = new FieldCell(sh, 2);
        when(sh.getRemainingCapacity()).thenReturn(1);
        Ship s = f.getShip();
        s.takeMissileHit();
        Assert.assertEquals(sh.getRemainingCapacity(), s.getRemainingCapacity());
        verify(sh, times(2)).getRemainingCapacity();
    }
}
