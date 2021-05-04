package org.pienkowski.rafal.room.allocation;

import org.pienkowski.rafal.room.api.InvalidAmountException;
import org.pienkowski.rafal.room.api.RoomType;

import java.math.BigDecimal;
import java.util.Objects;

public record Guest(BigDecimal amount, RoomType roomType) {

    public Guest {
        Objects.requireNonNull(amount, "Amount may not be null.");
        Objects.requireNonNull(roomType, "Room type may not be null.");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("Amount may not be lower than 0.");
        }
    }

    public static Guest from(PotentialGuest potentialGuest, RoomType roomType) {
        return new Guest(potentialGuest.amountWillingToPay(), roomType);
    }
}
