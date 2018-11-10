package com.github.christopheml.wowcompletionist.api.reference;

import com.github.christopheml.wowcompletionist.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class PetExclusionsTest {


    private PetExclusions petExclusions;

    @BeforeEach
    void setUp() throws IOException {
        petExclusions = new PetExclusions("/data/pets/exclusion_samples.json");
    }

    @Test
    void available_pet_for_all_regions() {
        for (Region region : Region.values()) {
            assertThat(petExclusions.isUnavailable(86081, region)).isFalse();
        }
    }

    @Test
    void unavailable_pet_for_all_regions() {
        for (Region region : Region.values()) {
            assertThat(petExclusions.isUnavailable(32841, region)).isTrue();
        }
    }

    @Test
    void unavailable_pet_for_specific_region() {
        assertThat(petExclusions.isUnavailable(16069, Region.US)).isTrue();
    }


    @Test
    void region_exclusive_pet() {
        assertThat(petExclusions.isUnavailable(15358, Region.EU)).isFalse();
    }

}
