package com.thoughtworks.battleship.common;

public class FormatUtil {
    public static Location strToLoc(String s) {
        if(s == null || s.length() != 2)
            throw new IllegalArgumentException("Illegal : Only string of size 2 valid");
        return new Location(s.toUpperCase().charAt(0) - 'A' + 1, s.charAt(1) - '0');
    }

    public static String LocToStr(Location l) {
        if(l == null)
            throw new IllegalArgumentException("Illegal : Location can not be null");
        StringBuilder sb = new StringBuilder();
        sb.append((char)(l.getRow()-1 + 'A'));
        sb.append((char)(l.getCol() + '0'));
        return sb.toString();
    }
}


