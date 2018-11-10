package com.github.christopheml.wowcompletionist.api.reference;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.christopheml.wowcompletionist.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PetExclusions {

    private Map<Region, Set<Long>> regionExclusivePets;

    private Set<Long> globallyUnavailablePets;

    public PetExclusions(@Value("${localData.pets.exclusions}") String fileLocation) throws IOException {
        load(fileLocation);
    }

    /**
     * Indicates if a pet is still available in-game.
     *
     * <p>A given pet can be unavailable globally (e.g. tied to a past event) or can be unavailable in specific
     * regions (e.g. EU-only extra Burning Crusade collector's edition pet)</p>
     */
    public boolean isUnavailable(long creatureId, Region region) {
        return isUnavailableGlobally(creatureId) || isUnavailableForRegion(creatureId, region);
    }

    private boolean isUnavailableGlobally(long creatureId) {
        return globallyUnavailablePets.contains(creatureId);
    }

    private boolean isUnavailableForRegion(long creatureId, Region region) {
        for (Region anotherRegion : Region.values()) {
            if (region != anotherRegion && isExclusiveToRegion(creatureId, anotherRegion)) {
                return true;
            }
        }
        return false;
    }

    private boolean isExclusiveToRegion(long creatureId, Region region) {
        return regionExclusivePets.containsKey(region) && regionExclusivePets.get(region).contains(creatureId);
    }

    private void load(String fileLocation) throws IOException {
        try (InputStream dataFile = getClass().getResourceAsStream(fileLocation)) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(dataFile);

            globallyUnavailablePets = loadGloballyUnavailablePets(objectMapper, root);
            regionExclusivePets = localRegionExclusivePets(objectMapper, root);
        }
    }

    private Map<Region, Set<Long>> localRegionExclusivePets(ObjectMapper objectMapper, JsonNode root) throws IOException {
        ObjectReader regionExclusivesReader = objectMapper.readerFor(new TypeReference<Map<String, Set<Long>>>() {});
        Map<String, Set<Long>> regionExclusives = regionExclusivesReader.readValue(root.get("regionExclusives"));

        return regionExclusives.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> Region.from(e.getKey()),
                        Map.Entry::getValue
                ));
    }

    private Set<Long> loadGloballyUnavailablePets(ObjectMapper objectMapper, JsonNode root) throws IOException {
        ObjectReader unavailableReader = objectMapper.readerFor(new TypeReference<Set<Long>>() {});
        return unavailableReader.readValue(root.get("unavailable"));
    }

}
