package com.thoughtworks.battleship.common;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class FormatUtilTest {
    Location loc = mock(Location.class);

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgExpForInvalidStringToLocConv() {
        FormatUtil.strToLoc(null);
        FormatUtil.strToLoc("ABC");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgExpForInvalidLocToStrConv() {
        FormatUtil.LocToStr(null);
    }

    @Test
    public void testLocToStrConvForValidInp() {
        when(loc.getRow()).thenReturn(2);
        when(loc.getCol()).thenReturn(3);
        Assert.assertTrue(FormatUtil.LocToStr(loc).equals("B3"));
        verify(loc, times(1)).getCol();
        verify(loc, times(1)).getRow();
    }

    @Test
    public void testStrToLocConvForValidInp() {
        Location l = FormatUtil.strToLoc("B1");
        Assert.assertEquals(2, l.getRow());
        Assert.assertEquals(1, l.getCol());
    }
}
