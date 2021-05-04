package org.pienkowski.rafal.room.allocation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.pienkowski.rafal.room.api.HotelUsage;
import org.pienkowski.rafal.room.policy.PolicyTestProvider;
import org.pienkowski.rafal.room.policy.RoomsAllocationPolicy;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class HotelServiceTest {

    private final GuestsMapper guestsMapper = new GuestsMapper();

    private final RoomsAllocationPolicy allocationPolicy = PolicyTestProvider.createPremiumEconomyPolicy();

    private final HotelService hotelService = new HotelService(guestsMapper, allocationPolicy);

    private final List<BigDecimal> potentialGuests = List.of(
            BigDecimal.valueOf(23),
            BigDecimal.valueOf(45),
            BigDecimal.valueOf(155),
            BigDecimal.valueOf(374),
            BigDecimal.valueOf(22),
            BigDecimal.valueOf(99.99),
            BigDecimal.valueOf(100),
            BigDecimal.valueOf(101),
            BigDecimal.valueOf(115),
            BigDecimal.valueOf(209)
    );

    @ParameterizedTest
    @MethodSource("roomsAndTotalProvider")
    void shouldAllocatePotentialGuests(int freePremiumRoomsCount, int freeEconomyRoomsCount,
                                       int expectedPremiumRoomsCount, int expectedEconomyRoomsCount,
                                       BigDecimal expectedPremiumTotal, BigDecimal expectedEconomyTotal) {
        // when
        HotelUsage hotelUsage = hotelService.allocate(potentialGuests, freePremiumRoomsCount, freeEconomyRoomsCount);

        // then
        assertEquals(expectedPremiumRoomsCount, hotelUsage.premium().count());
        assertEquals(expectedEconomyRoomsCount, hotelUsage.economy().count());
        assertEquals(expectedPremiumTotal, hotelUsage.premium().total().amount());
        assertEquals(expectedEconomyTotal, hotelUsage.economy().total().amount());
    }

    static Stream<Arguments> roomsAndTotalProvider() {
        return Stream.of(
                arguments(3, 3, 3, 3, BigDecimal.valueOf(738), BigDecimal.valueOf(167.99)),
                arguments(7, 5, 6, 4, BigDecimal.valueOf(1054), BigDecimal.valueOf(189.99)),
                arguments(2, 7, 2, 4, BigDecimal.valueOf(583), BigDecimal.valueOf(189.99)),
                arguments(7, 1, 7, 1, BigDecimal.valueOf(1153.99), BigDecimal.valueOf(45)),
                arguments(12, 1, 9, 1, BigDecimal.valueOf(1221.99), BigDecimal.valueOf(22)),
                arguments(0, 0, 0, 0, BigDecimal.ZERO, BigDecimal.ZERO),
                arguments(0, 12, 0, 4, BigDecimal.ZERO, BigDecimal.valueOf(189.99)),
                arguments(2, 12, 2, 4, BigDecimal.valueOf(583), BigDecimal.valueOf(189.99)),
                arguments(12, 12, 6, 4, BigDecimal.valueOf(1054), BigDecimal.valueOf(189.99)),
                arguments(2, 2, 2, 2, BigDecimal.valueOf(583), BigDecimal.valueOf(144.99)),
                arguments(1, 6, 1, 4, BigDecimal.valueOf(374), BigDecimal.valueOf(189.99)),
                arguments(12, 0, 10, 0, BigDecimal.valueOf(1243.99), BigDecimal.ZERO)
        );
    }

}
