package com.github.christopheml.wowcompletionist.api.reference;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.christopheml.wowcompletionist.api.model.Pet;
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

    private List<Pet> pets;

    public PetDatabase(@Value("${localData.pets}") String fileLocation) throws IOException {
        load(fileLocation);
    }

    private void load(String fileLocation) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputStream dataFile = getClass().getResourceAsStream(fileLocation);
        pets = objectMapper.readValue(dataFile, new TypeReference<List<Pet>>() {});
    }

    public List<Pet> missingPets(List<Pet> collectedPets) {
        Set<Integer> collectedIds = collectedPets.stream()
                .map(Pet::getCreatureId)
                .collect(Collectors.toSet());
        return pets.stream()
                .filter(p -> !collectedIds.contains(p.getCreatureId()))
                .collect(Collectors.toList());
    }

}
