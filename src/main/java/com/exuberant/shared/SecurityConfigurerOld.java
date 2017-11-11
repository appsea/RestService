package com.exuberant.shared;

import com.exuberant.authentication.mongo.MongoAuthenticationManager;
import com.exuberant.authentication.mongo.MongoAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * Created by rakesh on 04-Nov-2017.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurerOld extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        System.err.println("Inside  configureGlobal");
        //auth.authenticationProvider()
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.err.println("Inside  configure....");
        //http.csrf().and().cors();
        http.csrf().disable().exceptionHandling()
                .and().cors().and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().formLogin()
                .usernameParameter("username").passwordParameter("password")
                .successHandler(mySuccessHandler())
                .failureHandler(failureHandler())
                .and()
                .authorizeRequests()
                //.antMatchers("/login").permitAll()
                .antMatchers("/sas/*").hasRole("USER")
                .and()
                .logout();
        /*http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/sas*//*").authenticated()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout().and().cors();*/
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler mySuccessHandler() {
        return new MySavedRequestAwareAuthenticationSuccessHandler();
    }

    /*@Bean
    public DelegatingFilterProxy springSecurityFilterChain() {
        return new DelegatingFilterProxy("usernamePasswordAuthenticationFilter");
    }*/

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(){
        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter();
        usernamePasswordAuthenticationFilter.setAuthenticationManager(new MongoAuthenticationManager(mongoAuthenticationProvider()));
        return usernamePasswordAuthenticationFilter;
    }

    @Bean
    public MongoAuthenticationManager mongoAuthenticationManager(){
        return new MongoAuthenticationManager(mongoAuthenticationProvider());
    }

    @Bean
    public MongoAuthenticationProvider mongoAuthenticationProvider(){
        return new MongoAuthenticationProvider();
    }
}