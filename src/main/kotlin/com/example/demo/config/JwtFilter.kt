package com.example.demo.config

import com.example.demo.service.AppUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var userDetailsService: AppUserDetailsService

    @Autowired
    lateinit var jwtUtil: JwtUtil

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var authorizationToken = request.getHeader("Authorization")
        var token: String = ""
        var username: String = ""
        if (authorizationToken != null && authorizationToken.startsWith("Bearer ")) {
            token = authorizationToken.substring(7)
            try {
                username = jwtUtil.getUsernameFromToken(token)
            } catch (ex: IllegalArgumentException) {
                logger.error("unable to get jwt token")
            }
        } else {
            logger.error("Bearer String not found")
        }
        if (username != "" && SecurityContextHolder.getContext().authentication == null) {
            var userDetails = userDetailsService.loadUserByUsername(username)
            if (jwtUtil.validateToken(username, token)) {
                var authToken: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }

        filterChain.doFilter(request, response)
    }
}