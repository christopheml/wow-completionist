package com.github.christopheml.wowcompletionist.api.reference;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.christopheml.wowcompletionist.Region;
import com.github.christopheml.wowcompletionist.api.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Loads a database of all existing battle pets and companions.
 *
 * <p>While data can be queried live from the Battle.net API, while there is no persistence in the application,
 * a local version will be used instead.</p>
 */
@Component
public class PetDatabase {

    private final PetExclusions petExclusions;

    private List<Pet> pets;

    @Autowired
    public PetDatabase(@Value("${localData.pets}") String fileLocation, PetExclusions petExclusions) throws IOException {
        this.petExclusions = petExclusions;
        load(fileLocation);
    }

    private void load(String fileLocation) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputStream dataFile = getClass().getResourceAsStream(fileLocation);
        pets = objectMapper.readValue(dataFile, new TypeReference<List<Pet>>() {});
    }

    public List<Pet> missingPets(List<Pet> collectedPets, Region region) {
        Set<Integer> collectedIds = collectedPets.stream()
                .map(Pet::getCreatureId)
                .collect(Collectors.toSet());
        return pets.stream()
                .filter(p -> !collectedIds.contains(p.getCreatureId()))
                .filter(p -> !petExclusions.isUnavailable(p.getCreatureId(), region))
                .collect(Collectors.toList());
    }

}
