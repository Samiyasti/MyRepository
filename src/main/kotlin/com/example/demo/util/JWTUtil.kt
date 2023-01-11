package com.example.demo.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import kotlin.reflect.KFunction1

@Component
class JWTUtil {
    private val SECRET_STRING:String="MyNameSayan"
    fun generateToken(username:String):String{
        var claims:Map<String,Object> =HashMap<String,Object>()
        return createToken(claims,username)
    }

    private fun createToken(claims: Map<String, Object>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis()+1000*60*60)).signWith(SignatureAlgorithm.HS256,SECRET_STRING).compact()
    }

    fun validateToken(token:String,userDetails: UserDetails):Boolean{
        val username:String=extractUserName(token).toString()
        return (username.equals(userDetails.username) && !tokenExpired(token))
    }

    private fun extractExpiryTime(token: String): Any {
        return getClaimFromToken(token,Claims::getExpiration)
    }

    private fun tokenExpired(token: String): Boolean{
        return Date(extractExpiryTime(token).toString()).before(Date(System.currentTimeMillis()))
    }

    private fun extractUserName(token: String): (Claims) -> Any {
        return getClaimFromToken(token,Claims::getSubject)
    }

    private fun getClaimFromToken(token: String, claimResolver: KFunction1<Claims, Any>): (Claims) -> Any {
        val claims:Claims=getAllClaims(token)
        return claimResolver.apply { claims }
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(SECRET_STRING).parseClaimsJws(token).body
    }
}
