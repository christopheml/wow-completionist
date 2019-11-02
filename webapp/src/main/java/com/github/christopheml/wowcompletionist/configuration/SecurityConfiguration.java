package com.github.christopheml.wowcompletionist.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@PropertySource({"classpath:configuration/admin.properties"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String adminUser;
    private final String adminPassword;

    @Autowired
    public SecurityConfiguration(@Value("${admin.username}") String adminUser,
                                 @Value("${admin.password}") String adminPassword) {
        this.adminUser = adminUser;
        this.adminPassword = adminPassword;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Use admin credentials from configuration, in memory, with a placeholder noop password encoder for now (bad!)
        auth.inMemoryAuthentication()
                .withUser(adminUser)
                .password("{noop}" + adminPassword)
                .roles("ADMIN");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // Endpoints are all public by default, save for the administration area.
        http.authorizeRequests()
                .antMatchers("/admin/**").authenticated()
                .antMatchers("/**").permitAll()
                .and()
            .httpBasic();

        // Allows POST requests without csrf (for character selection)
        http.csrf()
                .ignoringAntMatchers("/");
    }

}
