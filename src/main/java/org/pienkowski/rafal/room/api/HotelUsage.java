package org.pienkowski.rafal.room.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public record HotelUsage(@JsonProperty("premium") RoomsUsage premium,
                         @JsonProperty("economy") RoomsUsage economy) {

    public HotelUsage {
        Objects.requireNonNull(premium, "Usage of premium rooms may not be null.");
        Objects.requireNonNull(premium, "Usage of economy rooms may not be null.");
        if (!premium.roomType().equals(RoomType.PREMIUM)) {
            throw new WrongRoomTypeException("Premium rooms usage must be for premium rooms only");
        }
        if (!economy.roomType().equals(RoomType.ECONOMY)) {
            throw new WrongRoomTypeException("Economy rooms usage must be for economy rooms only");
        }
    }
}
