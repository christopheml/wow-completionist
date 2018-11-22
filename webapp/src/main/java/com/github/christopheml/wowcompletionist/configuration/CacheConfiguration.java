package com.github.christopheml.wowcompletionist.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration  {

    // EhCache configuration is defined in resources/configuration/ehcache.xml

}
