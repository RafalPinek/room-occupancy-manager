package org.pienkowski.rafal.room.allocation;

public class RoomsCapacityTestProvider {

    public static RoomsCapacity createPremium(Integer count) {
        return new PremiumRoomsCapacity(count);
    }

    public static RoomsCapacity createEconomy(Integer count) {
        return new EconomyRoomsCapacity(count);
    }
}
