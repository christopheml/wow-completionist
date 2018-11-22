package com.github.christopheml.wowcompletionist.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("/configuration/local-data.properties")
public class LocalDataConfiguration {

}
