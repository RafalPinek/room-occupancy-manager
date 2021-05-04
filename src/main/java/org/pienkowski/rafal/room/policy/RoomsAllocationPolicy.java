package org.pienkowski.rafal.room.policy;

import org.pienkowski.rafal.room.allocation.Guest;
import org.pienkowski.rafal.room.allocation.PotentialGuest;
import org.pienkowski.rafal.room.allocation.RoomsCapacity;
import org.pienkowski.rafal.room.api.RoomType;

import java.util.List;

public interface RoomsAllocationPolicy {

    List<Guest> allocate(List<PotentialGuest> potentialGuests, List<RoomsCapacity> roomsCapacities);

    default Integer countOfType(List<RoomsCapacity> roomsCapacities, RoomType roomType) {
        return roomsCapacities.stream()
                .filter(roomsCapacity -> roomsCapacity.type().equals(roomType))
                .findAny()
                .map(RoomsCapacity::count)
                .orElse(0);
    }

}
