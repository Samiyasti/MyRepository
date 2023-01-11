package com.example.demo.service

import com.example.demo.repository.MyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService:UserDetailsService {
    @Autowired
    lateinit var repo: MyRepository
    override fun loadUserByUsername(userName: String?): UserDetails ?{
        var user: com.example.demo.model.User?= userName?.let { repo.findByUserName(it) }
        var resultUser: User? =null
        if (user != null) {
            resultUser= User(user.userName,user.password, ArrayList<GrantedAuthority>())
        }
        return resultUser
    }

}
