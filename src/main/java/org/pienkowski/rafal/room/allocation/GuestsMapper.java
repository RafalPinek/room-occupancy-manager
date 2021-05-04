package org.pienkowski.rafal.room.allocation;

import org.pienkowski.rafal.room.HotelConsts;
import org.pienkowski.rafal.room.api.HotelUsage;
import org.pienkowski.rafal.room.api.Money;
import org.pienkowski.rafal.room.api.RoomType;
import org.pienkowski.rafal.room.api.RoomsUsage;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
class GuestsMapper {

    List<PotentialGuest> toPotentialGuests(List<BigDecimal> potentialGuests) {
        return potentialGuests.stream()
                .map(PotentialGuest::new)
                .collect(Collectors.toList());
    }

    HotelUsage toHotelUsage(List<Guest> guests) {
        RoomsUsage premiumUsage = createRoomsUsage(guests, RoomType.PREMIUM);
        RoomsUsage economyUsage = createRoomsUsage(guests, RoomType.ECONOMY);

        return new HotelUsage(premiumUsage, economyUsage);

    }

    private RoomsUsage createRoomsUsage(List<Guest> guests, RoomType roomType) {
        return new RoomsUsage(roomType, Long.valueOf(guests.stream()
                .filter(g -> g.roomType().equals(roomType))
                .count())
                .intValue(),
                new Money(guests.stream()
                        .filter(g -> g.roomType().equals(roomType))
                        .map(Guest::amount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add), HotelConsts.CURRENCY));
    }

}
