package com.exuberant.rest.shared.authentication;

import com.exuberant.rest.shared.authentication.mongo.MongoAuthenticationManager;
import com.exuberant.rest.shared.authentication.mongo.MongoAuthenticationProvider;
import com.exuberant.rest.survey.config.SurveyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by rakesh on 04-Nov-2017.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(SurveyConfiguration.class)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    /*@Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SuccessfulAuthenticationHandler();
    }
*/
    /*@Bean
    public DelegatingFilterProxy springSecurityFilterChain() {
        return new DelegatingFilterProxy("usernamePasswordAuthenticationFilter");
    }*/

    /*@Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(){
        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter();
        usernamePasswordAuthenticationFilter.setAuthenticationManager(new MongoAuthenticationManager(mongoAuthenticationProvider()));
        return usernamePasswordAuthenticationFilter;
    }*/

    @Bean
    public MongoAuthenticationManager mongoAuthenticationManager() {
        return new MongoAuthenticationManager(mongoAuthenticationProvider());
    }

    @Bean
    public MongoAuthenticationProvider mongoAuthenticationProvider() {
        return new MongoAuthenticationProvider();
    }
}