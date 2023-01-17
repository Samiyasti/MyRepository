package com.example.demo.controller

import com.example.demo.config.JwtUtil
import com.example.demo.exception.InvalidCredentialsException
import com.example.demo.model.Credentials
import com.example.demo.service.AppUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtUtil: JwtUtil

    @Autowired
    lateinit var userDetailsService: AppUserDetailsService

    @PostMapping("/authenticated")
    fun getAuthenticated(@RequestBody credentials: Credentials): ResponseEntity<String> {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    credentials.username,
                    credentials.password
                )
            )
        } catch (ex: BadCredentialsException) {
            throw InvalidCredentialsException()
        }
        var jwtToken: String = jwtUtil.generateToken(username = userDetailsService.userDetails.username)
        return ResponseEntity.ok(jwtToken)
    }
}