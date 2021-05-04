package org.pienkowski.rafal.room.api;

import org.pienkowski.rafal.room.allocation.HotelService;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
class PremiumEconomyRoomsController {

    private final HotelService hotelService;

    public PremiumEconomyRoomsController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping(value = "/allocate")
    HotelUsage calculateHotelUsage(@NonNull @RequestParam("premium") Integer premiumCount,
                                   @NonNull @RequestParam("economy") Integer economyCount,
                                   @NonNull @RequestParam("potential-guests") List<BigDecimal> potentialGuests) {
        return hotelService.allocate(potentialGuests, premiumCount, economyCount);
    }
}
