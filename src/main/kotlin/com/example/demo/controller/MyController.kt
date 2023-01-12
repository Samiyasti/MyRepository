package com.example.demo.controller

import com.example.demo.model.AppUser
import com.example.demo.repository.AppUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class MyController {

    @Autowired
    lateinit var appUserRepository: AppUserRepository

    @Autowired
    lateinit var passwordEncoder: BCryptPasswordEncoder

    @GetMapping("/{username}")
    fun getUserByUserName(@PathVariable("username") username: String): ResponseEntity<String>{
        return ResponseEntity.ok().body(appUserRepository.findByUsername(username).toString())
    }

    @GetMapping("/home")
    fun getUsers():List<AppUser>{
        return  appUserRepository.findAll()
    }

    @PostMapping("/adduser")
    fun addUser(@RequestBody user: AppUser):AppUser{
        return appUserRepository.save(AppUser(user.id,user.username,user.firstName,user.lastName,passwordEncoder.encode(user.password),user.role))
    }

    @DeleteMapping("/deleteuser/{username}")
    fun deleteUser(@PathVariable("username") username: String):ResponseEntity<String>{
            appUserRepository.deleteByUsername(username)
            return ResponseEntity.ok().body("$username deleted successfully")
    }

}