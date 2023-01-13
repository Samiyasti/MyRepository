package com.example.demo.controller

import com.example.demo.model.AppUser
import com.example.demo.model.AppUserAddress
import com.example.demo.model.AppUserData
import com.example.demo.repository.AppUserAddressRepository
import com.example.demo.repository.AppUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.transaction.Transactional

@RestController
@RequestMapping("/user")
class MyController {

    @Autowired
    lateinit var appUserRepository: AppUserRepository

    @Autowired
    lateinit var appUserAddressRepository: AppUserAddressRepository

    @Autowired
    lateinit var passwordEncoder: BCryptPasswordEncoder

    @GetMapping("/{username}")
    fun getUserByUserName(@PathVariable("username") username: String): AppUser {
        return appUserRepository.findByUsername(username)[0]
    }

    @GetMapping("/home")
    fun getUsers(): List<AppUser> {
        return appUserRepository.findAll()
    }

    @PostMapping("/adduser")
    fun addUser(@RequestBody userData: AppUserData): List<AppUser> {
        userData.password = passwordEncoder.encode(userData.password)
        val user = AppUser(
            username = userData.username,
            firstName = userData.firstName,
            password = userData.password,
        )
        var listOfAddress= ArrayList<AppUserAddress>()
        userData.address.forEach{address->
            run {
                listOfAddress.add(

                        AppUserAddress(
                            address = address,
                            appUser = user
                        )

                )
            }
        }
        user.address = listOfAddress
        appUserRepository.save(user)
        return appUserRepository.findByUsername(userData.username)
    }

    @DeleteMapping("/deleteuser/{username}")
    @Transactional
    fun deleteUser(@PathVariable("username") username: String): ResponseEntity<String> {
        var noOfDeletedUser: Long = appUserRepository.deleteByUsername(username)
        if (noOfDeletedUser.toInt() == 0) {
            return ResponseEntity.ok().body("User with $username not found")
        }
        return ResponseEntity.ok().body("$username deleted successfully")
    }

}