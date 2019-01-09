package com.thoughtworks.battleship.common.exceptions;

public class InvalidInputException extends Exception {
    public InvalidInputException() {}
    public InvalidInputException(String message) {
        super("Invalid Input: "+ message);
    }
}
