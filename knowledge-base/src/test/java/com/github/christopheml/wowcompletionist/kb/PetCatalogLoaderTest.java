package com.github.christopheml.wowcompletionist.kb;

import com.github.christopheml.wowcompletionist.api.Region;
import com.github.christopheml.wowcompletionist.api.model.Pet;
import com.github.christopheml.wowcompletionist.api.services.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.jdbc.JdbcTestUtils;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

class PetCatalogLoaderTest {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PetService petService;
    private final PetCatalogLoader loader;

    public PetCatalogLoaderTest() {
        EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("database/schema.sql")
                .build();

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        petService = mock(PetService.class);
        loader = new PetCatalogLoader(jdbcTemplate, petService);
    }

    /**
     * Verifies that loading works on empty database.
     */
    @Test
    void insertion_into_empty_database() {
        when(petService.getAll(Region.EU)).thenReturn(asList(
                Pet.of(143175, 3, "Abyssal Eel", "inv_seaeel_teal", true),
                Pet.of(90202, 3, "Abyssius", "inv_pet_abyssal_blue", true)
        ));

        loader.loadForRegion(Region.EU);

        int petCount = JdbcTestUtils.countRowsInTable(jdbcTemplate.getJdbcTemplate(), "pets");
        assertThat(petCount).isEqualTo(2);
    }

    /**
     * Verifies that existing pets are properly updated upon loading.
     */
    @Test
    void insertion_with_existing_pets_present() {
        jdbcTemplate.getJdbcTemplate().update("insert into pets values (143175, 3, 'Abyssal Wheel', 'inv_seaeel_teal', true)");

        when(petService.getAll(Region.EU)).thenReturn(asList(
                Pet.of(143175, 3, "Abyssal Eel", "inv_seaeel_teal", true),
                Pet.of(90202, 3, "Abyssius", "inv_pet_abyssal_blue", true)
        ));

        loader.loadForRegion(Region.EU);

        int petCountWithOldName = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate.getJdbcTemplate(), "pets", "name = 'Abyssal Wheel'");
        assertThat(petCountWithOldName).isEqualTo(0);
    }

}
