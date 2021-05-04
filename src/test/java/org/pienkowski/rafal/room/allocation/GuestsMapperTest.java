package org.pienkowski.rafal.room.allocation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pienkowski.rafal.room.HotelConsts;
import org.pienkowski.rafal.room.api.HotelUsage;
import org.pienkowski.rafal.room.api.Money;
import org.pienkowski.rafal.room.api.RoomType;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class GuestsMapperTest {

    private final GuestsMapper mapper = new GuestsMapper();

    @Test
    public void shouldConvertToPotentialGuests() {
        // given
        List<BigDecimal> candidatesAmounts = List.of(
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

        // when
        List<PotentialGuest> potentialGuests = mapper.toPotentialGuests(candidatesAmounts);

        // then
        Assertions.assertThat(potentialGuests.size()).isEqualTo(candidatesAmounts.size());
        Assertions.assertThat(candidatesAmounts).containsExactlyInAnyOrder(
                potentialGuests.stream()
                        .map(PotentialGuest::amountWillingToPay)
                        .collect(Collectors.toList()).toArray(new BigDecimal[]{})
        );
    }

    @Test
    public void shouldCreateCorrectHotelUsageBasedOnAlreadyAllocatedGuests() {
        // given
        List<Guest> guests = List.of(
                new Guest(BigDecimal.valueOf(10), RoomType.ECONOMY),
                new Guest(BigDecimal.valueOf(20), RoomType.ECONOMY),
                new Guest(BigDecimal.valueOf(30), RoomType.ECONOMY),
                new Guest(BigDecimal.valueOf(50), RoomType.PREMIUM),
                new Guest(BigDecimal.valueOf(100), RoomType.PREMIUM),
                new Guest(BigDecimal.valueOf(150), RoomType.PREMIUM),
                new Guest(BigDecimal.valueOf(200), RoomType.PREMIUM),
                new Guest(BigDecimal.valueOf(300), RoomType.PREMIUM)
        );

        // when
        HotelUsage hotelUsage = mapper.toHotelUsage(guests);

        // then
        assertionsForEconomy(hotelUsage);
        assertionsForPremium(hotelUsage);
    }

    private void assertionsForEconomy(HotelUsage hotelUsage) {
        Assertions.assertThat(hotelUsage.economy().count()).isEqualTo(3);
        Assertions.assertThat(hotelUsage.economy().roomType()).isEqualTo(RoomType.ECONOMY);
        Assertions.assertThat(hotelUsage.economy().total()).isEqualTo(new Money(BigDecimal.valueOf(60), HotelConsts.CURRENCY));
    }

    private void assertionsForPremium(HotelUsage hotelUsage) {
        Assertions.assertThat(hotelUsage.premium().count()).isEqualTo(5);
        Assertions.assertThat(hotelUsage.premium().roomType()).isEqualTo(RoomType.PREMIUM);
        Assertions.assertThat(hotelUsage.premium().total()).isEqualTo(new Money(BigDecimal.valueOf(800), HotelConsts.CURRENCY));
    }
}
