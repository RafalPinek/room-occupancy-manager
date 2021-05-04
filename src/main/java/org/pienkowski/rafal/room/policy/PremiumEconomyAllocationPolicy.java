package org.pienkowski.rafal.room.policy;

import org.pienkowski.rafal.room.allocation.Guest;
import org.pienkowski.rafal.room.allocation.PotentialGuest;
import org.pienkowski.rafal.room.allocation.RoomsCapacity;
import org.pienkowski.rafal.room.api.RoomType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
class PremiumEconomyAllocationPolicy implements RoomsAllocationPolicy {

    @Override
    public List<Guest> allocate(List<PotentialGuest> potentialGuests, List<RoomsCapacity> roomsCapacities) {
        int freePremiumRooms = countOfType(roomsCapacities, RoomType.PREMIUM);
        int freeEconomyRooms = countOfType(roomsCapacities, RoomType.ECONOMY);

        List<Guest> premiumGuests = potentialGuests.stream()
                .filter(PotentialGuest::wantsPremiumOnly)
                .sorted(Comparator.comparing(PotentialGuest::amountWillingToPay).reversed())
                .limit(freePremiumRooms)
                .map(pg -> Guest.from(pg, RoomType.PREMIUM))
                .collect(Collectors.toList());

        List<Guest> guests = new ArrayList<>(premiumGuests);
        int remainingPremiumRooms = freePremiumRooms - premiumGuests.size();

        if (canAdditionalPremiumRoomsBeUsed(potentialGuests, freeEconomyRooms, remainingPremiumRooms)) {
            guests.addAll(getEconomyAndAdditionalPremiumGuests(potentialGuests, freeEconomyRooms, remainingPremiumRooms));
        } else {
            guests.addAll(getEconomyGuests(potentialGuests, freeEconomyRooms));
        }

        return guests;
    }

    List<Guest> getEconomyAndAdditionalPremiumGuests(List<PotentialGuest> potentialGuests, int freeEconomyRooms, int remainingPremiumRooms) {
        int countOfGuestsThatDontWantOnlyPremium = guestsThatDontWantOnlyPremiumCount(potentialGuests);
        int skipForEconomy = countOfGuestsThatDontWantOnlyPremium - (freeEconomyRooms + remainingPremiumRooms);
        skipForEconomy = Math.max(skipForEconomy, 0);

        List<Guest> economyGuests = potentialGuests.stream()
                .filter(pg -> !pg.wantsPremiumOnly())
                .sorted(Comparator.comparing(PotentialGuest::amountWillingToPay))
                .skip(skipForEconomy)
                .limit(freeEconomyRooms)
                .map(pg -> Guest.from(pg, RoomType.ECONOMY))
                .collect(Collectors.toList());

        List<Guest> additionalPremiumGuests = potentialGuests.stream()
                .filter(pg -> !pg.wantsPremiumOnly())
                .sorted(Comparator.comparing(PotentialGuest::amountWillingToPay))
                .skip(skipForEconomy + economyGuests.size())
                .map(pg -> Guest.from(pg, RoomType.PREMIUM))
                .collect(Collectors.toList());

        return Stream.concat(economyGuests.stream(), additionalPremiumGuests.stream())
                .collect(Collectors.toList());
    }

    private List<Guest> getEconomyGuests(List<PotentialGuest> potentialGuests, int freeEconomyRooms) {
        return potentialGuests.stream()
                .filter(pg -> !pg.wantsPremiumOnly())
                .sorted(Comparator.comparing(PotentialGuest::amountWillingToPay).reversed())
                .limit(freeEconomyRooms)
                .map(pg -> Guest.from(pg, RoomType.ECONOMY))
                .collect(Collectors.toList());
    }

    private boolean canAdditionalPremiumRoomsBeUsed(List<PotentialGuest> potentialGuests, int freeEconomyRooms, int remainingPremiumRooms) {
        return areTooManyEconomyGuests(potentialGuests, freeEconomyRooms) && remainingPremiumRooms > 0;
    }

    private boolean areTooManyEconomyGuests(List<PotentialGuest> potentialGuests, int freeEconomyRooms) {
        return guestsThatDontWantOnlyPremiumCount(potentialGuests) > freeEconomyRooms;
    }

    private int guestsThatDontWantOnlyPremiumCount(List<PotentialGuest> potentialGuests) {
        return Long.valueOf(potentialGuests.stream()
                .filter(pg -> !pg.wantsPremiumOnly())
                .count())
                .intValue();
    }
}
