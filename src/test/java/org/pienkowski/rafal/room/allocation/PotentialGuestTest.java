package org.pienkowski.rafal.room.allocation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pienkowski.rafal.room.api.InvalidAmountException;

import java.math.BigDecimal;

public class PotentialGuestTest {

    @Test
    public void shouldThrowExceptionForNullAmount() {
        // when and then
        Assertions.assertThrows(NullPointerException.class,
                () -> new PotentialGuest(null));
    }

    @Test
    public void shouldThrowExceptionForNegativeAmount() {
        // when and then
        Assertions.assertThrows(InvalidAmountException.class,
                () -> new PotentialGuest(BigDecimal.valueOf(-2L)));
    }

    @Test
    public void shouldCreatePotentialGuestForZeroAmount() {
        // when
        new PotentialGuest(BigDecimal.ZERO);

        // then no exception
    }

}
