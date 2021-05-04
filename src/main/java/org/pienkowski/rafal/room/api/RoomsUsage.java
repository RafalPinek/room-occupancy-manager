package org.pienkowski.rafal.room.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public record RoomsUsage(@JsonProperty("roomType") RoomType roomType,
                         @JsonProperty("count") Integer count,
                         @JsonProperty("total") Money total) {

    public RoomsUsage {
        Objects.requireNonNull(roomType, "Room type may not be null.");
        Objects.requireNonNull(count, "Count may not be null.");
        Objects.requireNonNull(total, "Total may not be null.");
        if (count < 0) {
            throw new InvalidAmountException("Count may not be lower than 0.");
        }
    }

}
