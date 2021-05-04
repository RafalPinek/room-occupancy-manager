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

    @Test
    public void shouldWantsPremiumOnly() {
        // when
        PotentialGuest potentialGuest = new PotentialGuest(BigDecimal.valueOf(300));

        // then
        Assertions.assertTrue(potentialGuest.wantsPremiumOnly());
    }

    @Test
    public void shouldDontWantPremiumOnly() {
        // when
        PotentialGuest potentialGuest = new PotentialGuest(BigDecimal.valueOf(40));

        // then
        Assertions.assertFalse(potentialGuest.wantsPremiumOnly());
    }

}
