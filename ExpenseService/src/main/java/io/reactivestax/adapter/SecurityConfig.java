package io.reactivestax.adapter;

//import com.allanditzel.springframework.security.web.csrf.CsrfTokenResponseHeaderBindingFilter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Profile("PROD")
@Configuration
//@EnableWebSecurity
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Using in memory  authentication  store , it can be replaced with jdbc  or external  id store
        auth.inMemoryAuthentication()
                .withUser("readonly").password("{noop}readonly123").roles("RO")
                .and()
                .withUser("admin").password("{noop}admin123").roles("RO", "ADMIN");
    }

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/**").hasRole("RO")
                .antMatchers(HttpMethod.POST, "/api/v1/**").hasRole("ADMIN")
               // .antMatchers(HttpMethod.POST, "/api/v1/users").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/**").permitAll()
                .and()
                .csrf().disable()
                .formLogin().disable();

        // https://github.com/aditzel/spring-security-csrf-filter
        // https://stackoverflow.com/questions/20862299/with-spring-security-3-2-0-release-how-can-i-get-the-csrf-token-in-a-page-that
        // http.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);
    }

}
