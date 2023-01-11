package com.example.demo.controller

import com.example.demo.model.User
import com.example.demo.service.MyService
import com.example.demo.util.JWTUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class MyController {
    @Autowired
    lateinit var myService: MyService
    @Autowired
    lateinit var jwtUtil: JWTUtil
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @GetMapping("/{firstName}")
    fun getUser(@PathVariable("firstName") firstName: String): List<User> {
        return myService.findUser(firstName)
    }

    @GetMapping
    fun home(): List<User> {
        return myService.findAllUser()
    }

    @PostMapping
    fun addUser(@RequestBody user: User): User {
        user.password = passwordEncoder.encode(user.password)
        myService.addUser(
            user
        )
        return myService.getUser(user)
    }

    @GetMapping("/{firstName}/{lastName}/{userName}/{password}/{role}")
    fun addUser1(
        @PathVariable("firstName") firstName: String,
        @PathVariable("lastName") lastName: String,
        @PathVariable("userName") userName: String,
        @PathVariable("password") password: String,
        @PathVariable("role") role: String
    ): User {
        val user: User = User(userName, firstName, lastName, passwordEncoder.encode(password), role)
        myService.addUser(user)
        return myService.getUser(user)
    }

    @DeleteMapping("/deleteuser/{userName}")
    fun deleteUser(@PathVariable("userName") firstName: String) {
        myService.deleteUser(firstName)
    }

    @PostMapping("/authenticate")
    fun generateToken(@RequestBody user: User):String{
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(user.userName,user.password)
            )
        }catch(e:Exception){
            throw Exception("Invalid Username or Password")
        }
        return jwtUtil.generateToken(user.userName)

    }
}