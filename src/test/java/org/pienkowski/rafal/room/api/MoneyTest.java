package org.pienkowski.rafal.room.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class MoneyTest {

    @Test
    public void shouldThrowExceptionForNullAmount() {
        // when and then
        Assertions.assertThrows(NullPointerException.class,
                () -> new Money(null, "EUR"));
    }

    @Test
    public void shouldThrowExceptionForNullCurrency() {
        // when and then
        Assertions.assertThrows(NullPointerException.class,
                () -> new Money(BigDecimal.TEN, null));
    }

    @Test
    public void shouldThrowExceptionForNegativeAmount() {
        // when and then
        Assertions.assertThrows(InvalidAmountException.class,
                () -> new Money(BigDecimal.valueOf(-2L), "USD"));
    }

    @Test
    public void shouldCreateMoneyWithZeroAmount() {
        // when
        Money money = new Money(BigDecimal.ZERO, "PLN");

        // then
        // no exception
        Assertions.assertEquals(money.amount(), BigDecimal.ZERO);
    }

}
