package com.thoughtworks.battleship.common;

import com.thoughtworks.battleship.common.Location;
import org.junit.Assert;
import org.junit.Test;

public class LocationTest {
    @Test
    public void testLocationCreationWithRowAndCol() {
        Location l = new Location(5, 20);
        Assert.assertEquals(5, l.getRow());
        Assert.assertEquals(20, l.getCol());
    }
}
