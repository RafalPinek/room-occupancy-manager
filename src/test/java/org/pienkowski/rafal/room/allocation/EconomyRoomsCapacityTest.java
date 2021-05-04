package org.pienkowski.rafal.room.allocation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pienkowski.rafal.room.api.InvalidAmountException;

public class EconomyRoomsCapacityTest {

    @Test
    public void shouldThrowExceptionForNullCount() {
        // when and then
        Assertions.assertThrows(NullPointerException.class,
                () -> new EconomyRoomsCapacity(null));
    }

    @Test
    public void shouldThrowExceptionForNegativeAmount() {
        // when and then
        Assertions.assertThrows(InvalidAmountException.class,
                () -> new EconomyRoomsCapacity(-1));
    }

    @Test
    public void shouldCreateCapacityForZeroCount() {
        // when
        new EconomyRoomsCapacity(0);

        // then no exception
    }

}
