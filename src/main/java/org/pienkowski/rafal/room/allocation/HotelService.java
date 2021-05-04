package org.pienkowski.rafal.room.allocation;

import org.pienkowski.rafal.room.api.HotelUsage;
import org.pienkowski.rafal.room.policy.RoomsAllocationPolicy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class HotelService {

    private final GuestsMapper mapper;

    private final RoomsAllocationPolicy allocationPolicy;

    public HotelService(GuestsMapper mapper, RoomsAllocationPolicy allocationPolicy) {
        this.mapper = mapper;
        this.allocationPolicy = allocationPolicy;
    }

    public HotelUsage allocate(List<BigDecimal> potentialGuests, Integer premium, Integer economy) {
        List<Guest> guests = allocationPolicy.allocate(mapper.toPotentialGuests(potentialGuests),
                List.of(new PremiumRoomsCapacity(premium),
                        new EconomyRoomsCapacity(economy)));
        return mapper.toHotelUsage(guests);
    }

}
