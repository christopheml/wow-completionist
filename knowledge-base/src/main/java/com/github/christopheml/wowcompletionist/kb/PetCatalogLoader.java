package com.github.christopheml.wowcompletionist.kb;

import com.github.christopheml.wowcompletionist.api.Region;
import com.github.christopheml.wowcompletionist.api.model.Pet;
import com.github.christopheml.wowcompletionist.api.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;

/**
 * Loads pets from Blizzard's API into the application database.
 */
@Service
public class PetCatalogLoader {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final PetService petService;

    @Autowired
    public PetCatalogLoader(NamedParameterJdbcTemplate jdbcTemplate, PetService petService) {
        this.jdbcTemplate = jdbcTemplate;
        this.petService = petService;
    }

    public void loadForRegion(Region region) {
        List<Pet> currentBlizzardPets = petService.getAll(region);

        removeExistingPets(currentBlizzardPets);

        jdbcTemplate.getJdbcTemplate().batchUpdate("insert into pets values (?, ?, ?, ?, ?)",
                currentBlizzardPets,
                currentBlizzardPets.size(),
                (ps, pet) -> {
                    ps.setInt(1, pet.getCreatureId());
                    ps.setInt(2, pet.getQualityId());
                    ps.setString(3, pet.getName());
                    ps.setString(4, pet.getIcon());
                    ps.setBoolean(5, pet.canBattle());
                }
        );
    }

    /**
     * Removes all pets in the list from the database.
     *
     * The easiest way to update pet data is to remove everything then reinsert all pets from Blizzard's API,
     * since this is a rare operation done once a patch. However, we want to keep data on pets that would be removed
     * from the game and absent from API responses, so we only remove pets that are part of the response.
     */
    private void removeExistingPets(List<Pet> currentBlizzardPets) {
        List<Integer> ids = currentBlizzardPets.stream().map(Pet::getCreatureId).collect(toList());
        jdbcTemplate.update("delete from pets where creatureId in (:ids)", singletonMap("ids", ids));
    }

}
