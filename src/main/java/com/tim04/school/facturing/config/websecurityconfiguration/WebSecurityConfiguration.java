package com.tim04.school.facturing.config.websecurityconfiguration;

import com.tim04.school.facturing.user.login.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MyUserDetailService userDetailsService;
/*    @Autowired
    private CustomAuthenticationFailureHandler customFailure;*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll();

        http
                .authorizeRequests()
                .antMatchers("/")
                .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
       /* http
                .authorizeRequests()
                .antMatchers("/")
                .access("");*/

        /*http.authorizeRequests()
                .antMatchers("/")
                .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
*/
        http.authorizeRequests()
                .and()
                .formLogin()
                .loginProcessingUrl("/login_securely")// submit request ( binding cu formularul  este facut aici )
                .loginPage("/login")
                .defaultSuccessUrl("/Invoice")
                .usernameParameter("mail")
                .passwordParameter("password")
                //.failureHandler(customFailure)
                //configure logout
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/assets/**")
                .antMatchers("/templates/**");

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder thisBCryptPasswordEncoder = new BCryptPasswordEncoder();
        return thisBCryptPasswordEncoder;
    }


}
