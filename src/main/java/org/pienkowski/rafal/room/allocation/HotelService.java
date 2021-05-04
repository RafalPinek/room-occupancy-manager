package org.pienkowski.rafal.room.allocation;

import groovy.util.logging.Slf4j;
import org.pienkowski.rafal.room.api.HotelUsage;
import org.pienkowski.rafal.room.policy.RoomsAllocationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class HotelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelService.class);

    private final GuestsMapper mapper;

    private final RoomsAllocationPolicy allocationPolicy;

    public HotelService(GuestsMapper mapper, RoomsAllocationPolicy allocationPolicy) {
        this.mapper = mapper;
        this.allocationPolicy = allocationPolicy;
    }

    public HotelUsage allocate(List<BigDecimal> potentialGuests, Integer premium, Integer economy) {
        LOGGER.info("Allocating {} potential guests...", potentialGuests.size());
        List<Guest> guests = allocationPolicy.allocate(mapper.toPotentialGuests(potentialGuests),
                List.of(new PremiumRoomsCapacity(premium),
                        new EconomyRoomsCapacity(economy)));
        LOGGER.info("Allocated {} guests.", guests.size());
        return mapper.toHotelUsage(guests);
    }

}
