package com.github.christopheml.wowcompletionist.web;

import com.github.christopheml.wowcompletionist.api.CharacterIdentity;
import com.github.christopheml.wowcompletionist.api.model.Character;
import com.github.christopheml.wowcompletionist.api.model.Pets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class BattlePetsServiceCacheTest {

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    private BattlePetsService battlePetsService;

    @Test
    void return_value_should_be_cached_after_first_call() {
        when(oAuth2RestTemplate.getForObject(anyString(), eq(Character.class))).thenReturn(sampleCharacter());
        CharacterIdentity characterIdentity = CharacterIdentity.of("eu", "hyjal", "giantstone");

        battlePetsService.fetch(characterIdentity);
        battlePetsService.fetch(characterIdentity);

        verify(oAuth2RestTemplate, times(1)).getForObject(anyString(), eq(Character.class));
    }

    private Character sampleCharacter() {
        Character character = new Character();
        ReflectionTestUtils.setField(character, "pets", new Pets());
        return character;
    }

    @Configuration
    @EnableCaching
    static class TestCacheConfiguration {

        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("characterPets");
        }

        @Bean
        OAuth2RestTemplate blizzardApiRestTemplate() {
            return Mockito.mock(OAuth2RestTemplate.class);
        }

        @Bean
        BattlePetsService battlePetsService(OAuth2RestTemplate oAuth2RestTemplate) {
            return new BattlePetsService(oAuth2RestTemplate);
        }

    }

}
