package com.example.demo.exceptionhandler

import com.example.demo.exception.InvalidCredentialsException
import com.example.demo.exception.UserMisMatchException
import com.example.demo.exception.UsernameNotPresentException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomExceptionHandler {
    @ExceptionHandler(UserMisMatchException::class)
    fun userNameMismatchException(exception: UserMisMatchException): ResponseEntity<String> {
        return ResponseEntity<String>(exception.exceptionMessage, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(UsernameNotPresentException::class)
    fun userNameMismatchException(exception: UsernameNotPresentException): ResponseEntity<String> {
        return ResponseEntity<String>(exception.exceptionMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun invalidCredentialsException(exception: InvalidCredentialsException):ResponseEntity<String>{
        return ResponseEntity("Invalid username or password",HttpStatus.UNAUTHORIZED)
    }
}