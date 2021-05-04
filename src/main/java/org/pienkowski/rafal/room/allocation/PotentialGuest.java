package org.pienkowski.rafal.room.allocation;

import org.pienkowski.rafal.room.HotelConsts;
import org.pienkowski.rafal.room.api.InvalidAmountException;

import java.math.BigDecimal;
import java.util.Objects;

public record PotentialGuest(BigDecimal amountWillingToPay) {

    public PotentialGuest {
        Objects.requireNonNull(amountWillingToPay, "amount may not be null.");
        if (amountWillingToPay.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("Amount willing to pay may not be lower than 0.");
        }
    }

    public boolean wantsPremiumOnly() {
        return amountWillingToPay.compareTo(HotelConsts.ONLY_PREMIUM_THRESHOLD) >= 0;
    }
}
