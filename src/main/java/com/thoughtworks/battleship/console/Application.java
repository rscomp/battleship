package com.thoughtworks.battleship.console;

import com.thoughtworks.battleship.common.FormatUtil;
import com.thoughtworks.battleship.common.Location;
import com.thoughtworks.battleship.common.enums.PlayerCode;
import com.thoughtworks.battleship.common.enums.ShipType;
import com.thoughtworks.battleship.gameengine.BattleShipGame;

import java.util.Scanner;

final class Application {
    public static BattleShipGame g;
    public static Scanner sc;

    public static void main(String[] args) {
        init();
        play();
    }

    private static void init() {
        g = new BattleShipGame();
        sc = new Scanner(System.in);
        setupBattleField();
        placeShips();
        loadMisssiles();
    }

    private static void setupBattleField() {
        while(true) {
            int width, height;
            try {
                while(true) {
                    System.out.println("Input battlefield width[1-9] and height[A-Z] (space separated)");
                    String[] boardDimension = sc.nextLine().split(" ");
                    if (boardDimension != null && boardDimension.length == 2) {
                        width = Integer.parseInt(boardDimension[0]);
                        height = boardDimension[1].charAt(0) - 'A' + 1;
                        break;
                    }
                    System.out.println("Invalid Input");
                }
                g.initBattleField(height, width);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void placeShips() {
        while(true){
            try{
                int noOfShips;
                while(true) {
                    try {
                        System.out.println("Enter number of ship each player will have");
                        noOfShips = Integer.parseInt(sc.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Input");
                    }
                }
                int i = 0;
                while(true) {
                    System.out.println("Enter ships details for each player");
                    String[] shipDetails = sc.nextLine().split(" ");
                    if(shipDetails.length != 5) {
                        System.out.println("Invalid Input");
                        continue;
                    }
                    try {

                        int w = Integer.parseInt(shipDetails[1]);
                        int h = Integer.parseInt(shipDetails[2]);
                        Location firstLoc = FormatUtil.strToLoc(shipDetails[3]);
                        Location secondLoc = FormatUtil.strToLoc(shipDetails[4]);
                        ShipType s = ShipType.valueOf(shipDetails[0].toUpperCase());

                        g.placeShip(PlayerCode.ONE, s, h, w, firstLoc);
                        g.placeShip(PlayerCode.TWO, s, h, w,secondLoc);
                        i++;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    if(i == noOfShips)
                        break;

                }
                break;
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private static void loadMisssiles() {
        while(true) {
            System.out.println("Enter first player attack locations");
            String[] firstPlayerMissiles = sc.nextLine().split(" ");
            for(int i = 0; i < firstPlayerMissiles.length; i++) {
                try {
                    Location l = FormatUtil.strToLoc(firstPlayerMissiles[i]);
                    g.addMissile(PlayerCode.ONE, l);
                } catch( Exception e ) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Enter second player attack locations");
            String[] secondPlayerMissiles = sc.nextLine().split(" ");
            for(int i = 0; i < secondPlayerMissiles.length; i++) {
                try {
                    Location l = FormatUtil.strToLoc(secondPlayerMissiles[i]);
                    g.addMissile(PlayerCode.TWO, l);
                } catch( Exception e ) {
                    System.out.println(e.getMessage());
                }
            }
            break;
        }
    }

    private static void play() {
        g.playGame();
    }
}
