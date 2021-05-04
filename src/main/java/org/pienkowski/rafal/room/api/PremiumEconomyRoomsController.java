package org.pienkowski.rafal.room.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Allocates potential guests in hotel and retrieves statistics.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully allocated guests"),
            @ApiResponse(code = 400, message = "Provided parameters are invalid"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @GetMapping(value = "/allocate")
    HotelUsage calculateHotelUsage(@ApiParam("free premium rooms count") @NonNull @RequestParam("premium") Integer premiumCount,
                                   @ApiParam("free economy rooms count") @NonNull @RequestParam("economy") Integer economyCount,
                                   @ApiParam("potential guests list, represented by the amount they willing to pay")
                                   @NonNull @RequestParam("potential-guests") List<BigDecimal> potentialGuests) {
        return hotelService.allocate(potentialGuests, premiumCount, economyCount);
    }
}
