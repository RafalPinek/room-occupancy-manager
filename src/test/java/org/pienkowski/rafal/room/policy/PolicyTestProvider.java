package org.pienkowski.rafal.room.policy;

public class PolicyTestProvider {

    public static RoomsAllocationPolicy createPremiumEconomyPolicy() {
        return new PremiumEconomyAllocationPolicy();
    }

}
