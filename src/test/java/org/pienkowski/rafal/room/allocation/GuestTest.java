package org.pienkowski.rafal.room.allocation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pienkowski.rafal.room.api.InvalidAmountException;
import org.pienkowski.rafal.room.api.RoomType;

import java.math.BigDecimal;

public class GuestTest {

    @Test
    public void shouldThrowExceptionForNullAmount() {
        // when and then
        Assertions.assertThrows(NullPointerException.class,
                () -> new Guest(null, RoomType.ECONOMY));
    }

    @Test
    public void shouldThrowExceptionForNullRoomType() {
        // when and then
        Assertions.assertThrows(NullPointerException.class,
                () -> new Guest(BigDecimal.TEN, null));
    }

    @Test
    public void shouldThrowExceptionForNegativeAmount() {
        // when and then
        Assertions.assertThrows(InvalidAmountException.class,
                () -> new Guest(BigDecimal.valueOf(-2L), RoomType.PREMIUM));
    }

    @Test
    public void shouldCreateGuestForZeroAmount() {
        // when
        new Guest(BigDecimal.ZERO, RoomType.PREMIUM);

        // then no exception
    }

}
