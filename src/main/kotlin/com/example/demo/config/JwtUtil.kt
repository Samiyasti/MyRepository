package com.example.demo.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*
@Component
class JwtUtil {
    private val SECRET_KEY = "IamSayan";

    fun generateToken(username: String): String {
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, SECRET_KEY).setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 10 * 60 * 1000)).compact().toString()
    }

    fun validateToken(username: String, token: String): Boolean {
        var claims: Claims = extractAllClaims(token)
        return (username.equals(claims.subject) && !claims.expiration.before(Date(System.currentTimeMillis())))
    }

    fun getUsernameFromToken(token: String):String{
        var claims=extractAllClaims(token)
        return claims.subject
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body
    }
}