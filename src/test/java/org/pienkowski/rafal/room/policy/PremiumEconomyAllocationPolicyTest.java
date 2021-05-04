package org.pienkowski.rafal.room.policy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.pienkowski.rafal.room.allocation.Guest;
import org.pienkowski.rafal.room.allocation.PotentialGuest;
import org.pienkowski.rafal.room.allocation.RoomsCapacity;
import org.pienkowski.rafal.room.allocation.RoomsCapacityTestProvider;
import org.pienkowski.rafal.room.api.RoomType;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PremiumEconomyAllocationPolicyTest {

    private final RoomsAllocationPolicy policy = new PremiumEconomyAllocationPolicy();

    private final List<PotentialGuest> potentialGuests = List.of(
            new PotentialGuest(BigDecimal.valueOf(23)),
            new PotentialGuest(BigDecimal.valueOf(45)),
            new PotentialGuest(BigDecimal.valueOf(155)),
            new PotentialGuest(BigDecimal.valueOf(374)),
            new PotentialGuest(BigDecimal.valueOf(22)),
            new PotentialGuest(BigDecimal.valueOf(99.99)),
            new PotentialGuest(BigDecimal.valueOf(100)),
            new PotentialGuest(BigDecimal.valueOf(101)),
            new PotentialGuest(BigDecimal.valueOf(115)),
            new PotentialGuest(BigDecimal.valueOf(209))
    );

    @ParameterizedTest
    @MethodSource("roomsInfoProvider")
    void shouldAllocatePotentialGuests(int freePremiumRoomsCount, int freeEconomyRoomsCount,
                                       int expectedPremiumRoomsCount, int expectedEconomyRoomsCount) {
        // given
        List<RoomsCapacity> roomsCapacities = List.of(
                RoomsCapacityTestProvider.createPremium(freePremiumRoomsCount),
                RoomsCapacityTestProvider.createEconomy(freeEconomyRoomsCount)
        );

        // when
        List<Guest> allocatedGuests = policy.allocate(potentialGuests, roomsCapacities);

        // then
        int premiumGuestsCount = Long.valueOf(allocatedGuests.stream()
                .filter(g -> g.roomType().equals(RoomType.PREMIUM))
                .count())
                .intValue();

        int economyGuestsCount = Long.valueOf(allocatedGuests.stream()
                .filter(g -> g.roomType().equals(RoomType.ECONOMY))
                .count())
                .intValue();

        assertEquals(expectedPremiumRoomsCount, premiumGuestsCount);
        assertEquals(expectedEconomyRoomsCount, economyGuestsCount);
    }

    static Stream<Arguments> roomsInfoProvider() {
        return Stream.of(
                arguments(3, 3, 3, 3),
                arguments(7, 5, 6, 4),
                arguments(2, 7, 2, 4),
                arguments(7, 1, 7, 1),
                arguments(12, 1, 9, 1),
                arguments(0, 0, 0, 0),
                arguments(0, 12, 0, 4),
                arguments(2, 12, 2, 4),
                arguments(12, 12, 6, 4),
                arguments(2, 2, 2, 2),
                arguments(1, 6, 1, 4),
                arguments(12, 0, 10, 0)
        );
    }

}
