package com.thoughtworks.battleship.gameengine.common.validator;

import com.thoughtworks.battleship.common.exceptions.InvalidInputException;

class ConstraintViolationException extends InvalidInputException {
    public ConstraintViolationException() {
        super();
    }
    public ConstraintViolationException(String message) {
        super("Constarint Violation: "+ message);
    }
}
