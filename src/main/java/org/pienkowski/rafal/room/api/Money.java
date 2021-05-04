package org.pienkowski.rafal.room.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

public record Money(@JsonProperty("amount") BigDecimal amount,
                    @JsonProperty("currency") String currency) {

    public Money {
        Objects.requireNonNull(amount, "Amount may not be null.");
        Objects.requireNonNull(currency, "Currency may not be null.");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("Amount may not be lower than 0.");
        }
    }
}
