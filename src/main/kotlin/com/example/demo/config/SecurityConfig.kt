package com.example.demo.config

import com.example.demo.service.AppUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
@EnableWebSecurity
class SecurityConfig :WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var userDetailsService: AppUserDetailsService
    @Bean
    fun getPasswordEncoder():BCryptPasswordEncoder{
        return BCryptPasswordEncoder()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService)
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests().antMatchers("/user/adduser").permitAll()
            .antMatchers("/user/deleteuser").hasRole("ADMIN").and().httpBasic()
            .and().authorizeRequests()
            .antMatchers("/user/home/**").permitAll().anyRequest().authenticated()
    }
}