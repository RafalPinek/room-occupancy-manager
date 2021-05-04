package org.pienkowski.rafal.room.allocation;

import org.pienkowski.rafal.room.api.InvalidAmountException;
import org.pienkowski.rafal.room.api.RoomType;

import java.util.Objects;

record PremiumRoomsCapacity(Integer count) implements RoomsCapacity {

    public PremiumRoomsCapacity {
        Objects.requireNonNull(count, "Capacity may not be null.");
        if (count < 0) {
            throw new InvalidAmountException("Capacity may not be lower than 0.");
        }
    }

    @Override
    public RoomType type() {
        return RoomType.PREMIUM;
    }
}
