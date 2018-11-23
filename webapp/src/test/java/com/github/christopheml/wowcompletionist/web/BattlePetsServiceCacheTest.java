package com.github.christopheml.wowcompletionist.web;

import com.github.christopheml.wowcompletionist.api.CharacterIdentity;
import com.github.christopheml.wowcompletionist.api.Region;
import com.github.christopheml.wowcompletionist.api.model.Pets;
import com.github.christopheml.wowcompletionist.api.services.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class BattlePetsServiceCacheTest {

    @Autowired
    private PetService petService;

    @Autowired
    private BattlePetsService battlePetsService;

    @Test
    void return_value_should_be_cached_after_first_call() {
        CharacterIdentity characterIdentity = CharacterIdentity.of(Region.EU, "hyjal", "giantstone");
        when(petService.masterList(eq(characterIdentity))).thenReturn(Optional.of(new Pets()));

        battlePetsService.fetch(characterIdentity);
        battlePetsService.fetch(characterIdentity);

        verify(petService, times(1)).masterList(eq(characterIdentity));
    }

    @Configuration
    @EnableCaching
    static class TestCacheConfiguration {

        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("characterPets");
        }

        @Bean
        PetService petService() {
            return Mockito.mock(PetService.class);
        }

        @Bean
        BattlePetsService battlePetsService(PetService petService) {
            return new BattlePetsService(petService);
        }

    }

}
