package org.pienkowski.rafal.room.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class HotelUsageTest {

    private static final Money VALID_MONEY = new Money(BigDecimal.TEN, "EUR");

    private static final RoomsUsage VALID_PREMIUM_ROOMS_USAGE = new RoomsUsage(RoomType.PREMIUM, 1, VALID_MONEY);

    private static final RoomsUsage VALID_ECONOMY_ROOMS_USAGE = new RoomsUsage(RoomType.ECONOMY, 1, VALID_MONEY);

    @Test
    public void shouldThrowExceptionForNullPremiumRoomsUsage() {
        // when and then
        Assertions.assertThrows(NullPointerException.class,
                () -> new HotelUsage(null, VALID_ECONOMY_ROOMS_USAGE));
    }

    @Test
    public void shouldThrowExceptionForNullEconomyRoomsUSage() {
        // when and then
        Assertions.assertThrows(NullPointerException.class,
                () -> new HotelUsage(VALID_PREMIUM_ROOMS_USAGE, null));
    }

    @Test
    public void shouldThrowExceptionForWrongRoomTypeForPremium() {
        // when and then
        Assertions.assertThrows(WrongRoomTypeException.class,
                () -> new HotelUsage(VALID_ECONOMY_ROOMS_USAGE, VALID_ECONOMY_ROOMS_USAGE));
    }

    @Test
    public void shouldThrowExceptionForWrongRoomTypeForEconomy() {
        // when and then
        Assertions.assertThrows(WrongRoomTypeException.class,
                () -> new HotelUsage(VALID_PREMIUM_ROOMS_USAGE, VALID_PREMIUM_ROOMS_USAGE));
    }

    @Test
    public void shouldCreateHotelUsageForValidData() {
        // when
        new HotelUsage(VALID_PREMIUM_ROOMS_USAGE, VALID_ECONOMY_ROOMS_USAGE);

        // then no exception
    }

}
