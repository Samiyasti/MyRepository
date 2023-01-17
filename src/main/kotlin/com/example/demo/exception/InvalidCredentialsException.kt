package com.example.demo.exception

import org.springframework.security.authentication.BadCredentialsException

class InvalidCredentialsException(
    private var exceptionMessage: String = ""
) : BadCredentialsException(exceptionMessage)