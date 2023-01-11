package com.example.demo.config

import com.example.demo.service.MyUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
@Configuration
@EnableWebSecurity
class SecurityConfigurer: WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var userDetailsService: MyUserDetailsService
    override fun configure(http: HttpSecurity) {
        http.csrf().disable().authorizeRequests().antMatchers("/authenticate")
            .permitAll().anyRequest().authenticated()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService)
    }
    
    @Bean
    fun getPasswordEncoder():PasswordEncoder{
        return BCryptPasswordEncoder()
    }


    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}