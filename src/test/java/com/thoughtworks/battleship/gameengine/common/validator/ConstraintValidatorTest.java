package com.thoughtworks.battleship.gameengine.common.validator;

import com.thoughtworks.battleship.gameengine.Board;
import org.junit.Assert;
import org.junit.Test;

public class ConstraintValidatorTest {
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentExpWhenValidationShipLoc(){
        try {
            ConstraintValidator.validateShipPlacementLocationWithInBoard(new Board(1, 2), null, null);
        } catch (ConstraintViolationException e) {
            Assert.fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentExpWhenValLocInField(){
        try {
            ConstraintValidator.validateBattleFieldLocationConstraints(null, new Board(1, 2));
        } catch (ConstraintViolationException e) {
            Assert.fail();
        }
    }

    @Test
    public void testBattleFieldSizeConstraintViolation() {
        try {
            ConstraintValidator.validateBattleFieldSizeConstraints(-1, 0);
        } catch( ConstraintViolationException e ) {
            Assert.assertFalse(false);
        }
        try {
            ConstraintValidator.validateBattleFieldSizeConstraints(10, 0);
        } catch( ConstraintViolationException e ) {
            Assert.assertFalse(false);
        }
    }
}
