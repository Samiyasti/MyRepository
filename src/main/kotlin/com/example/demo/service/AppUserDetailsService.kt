package com.example.demo.service

import com.example.demo.model.AppUser
import com.example.demo.repository.AppUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
@Service
class AppUserDetailsService :UserDetailsService {
    @Autowired
    lateinit var appUserRepository: AppUserRepository
    override fun loadUserByUsername(username: String): UserDetails {
        var appUser:AppUser=appUserRepository.findByUsername(username)
        return User(appUser.username,appUser.password, ArrayList<GrantedAuthority>())
    }
}