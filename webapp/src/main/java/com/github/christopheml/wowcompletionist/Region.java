package com.github.christopheml.wowcompletionist;

public enum Region {

    US, EU, KR, TW, CN, SEA;

    public static Region from(String regionCode) {
        for (Region region : values()) {
            if (region.name().equalsIgnoreCase(regionCode)) {
                return region;
            }
        }
        throw new InvalidRegionException(regionCode);
    }

    public static class InvalidRegionException extends RuntimeException {

        InvalidRegionException(String invalidRegionCode) {
            super(String.format("Invalid region code %s", invalidRegionCode));
        }

    }

}
