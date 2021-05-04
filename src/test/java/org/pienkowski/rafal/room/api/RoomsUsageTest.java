package org.pienkowski.rafal.room.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class RoomsUsageTest {

    private static final Money VALID_MONEY = new Money(BigDecimal.TEN, "EUR");

    @Test
    public void shouldThrowExceptionForNullRoomType() {
        // when and then
        Assertions.assertThrows(NullPointerException.class,
                () -> new RoomsUsage(null, 1, VALID_MONEY));
    }

    @Test
    public void shouldThrowExceptionForNullCount() {
        // when and then
        Assertions.assertThrows(NullPointerException.class,
                () -> new RoomsUsage(RoomType.ECONOMY, null, VALID_MONEY));
    }

    @Test
    public void shouldThrowExceptionForNullMoney() {
        // when and then
        Assertions.assertThrows(NullPointerException.class,
                () -> new RoomsUsage(RoomType.ECONOMY, 1, null));
    }

    @Test
    public void shouldThrowExceptionForNegativeAmount() {
        // when and then
        Assertions.assertThrows(InvalidAmountException.class,
                () -> new RoomsUsage(RoomType.ECONOMY, -1, VALID_MONEY));
    }

    @Test
    public void shouldCreateRoomsUsageForValidData() {
        // when
        new RoomsUsage(RoomType.PREMIUM, 1, VALID_MONEY);

        // then no exception
    }

}
