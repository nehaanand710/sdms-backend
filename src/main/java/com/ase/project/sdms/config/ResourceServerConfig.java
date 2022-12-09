package com.ase.project.sdms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    public ResourceServerConfig() {
        super();
    }

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/").anonymous()
                .antMatchers("/api/register").anonymous()
                .antMatchers("/api/admin/home").hasAnyRole("ADMIN")
                .antMatchers("/api/user/home").hasAnyRole("USER")
                .antMatchers("/api/saveHazards").permitAll()
                .antMatchers("/api/saveBasicInfo").permitAll()
                .antMatchers("/api/forgotPassword").permitAll()
                .antMatchers("/api/resetPassword").permitAll()
                .antMatchers("/api/simulation").permitAll()
                .antMatchers("/api/oauth/token").permitAll()
                .antMatchers("/api/dashboard").permitAll()
                .antMatchers("/api/getDashboardData").permitAll()
                .antMatchers("/api/sendOrIgnoreAlert").permitAll()
                .antMatchers("/api/addSensor").permitAll()
                .antMatchers("/api/updateSensor").permitAll()
                .antMatchers("/api/deleteSensor").permitAll()
                .antMatchers("/api/getSensorList").permitAll()
                .antMatchers("/api/updateProfile").permitAll()
                .antMatchers("/api/testResponse").permitAll()
                .antMatchers("/api/testResponse").permitAll()
                .antMatchers("/api/updateEventHistoryActionFlag").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable();
    }

}
