package com.example.demo.controller

import com.example.demo.exception.UserMisMatchException
import com.example.demo.exception.UsernameNotPresentException
import com.example.demo.model.AppUser
import com.example.demo.model.AppUserAddress
import com.example.demo.model.AppUserData
import com.example.demo.repository.AppUserAddressRepository
import com.example.demo.repository.AppUserRepository
import com.example.demo.service.AppUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
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

    @Autowired
    lateinit var userDetailsService: AppUserDetailsService

    @GetMapping("/{username}")
    fun getUserByUserName(@PathVariable("username") username: String): AppUser {
        try {
            return appUserRepository.findByUsername(username)
        } catch (ex: EmptyResultDataAccessException) {
            throw UsernameNotPresentException(exceptionMessage = "$username not found")
        }
    }

    @GetMapping("/home")
    fun getUsers(): Map<String, String> {
        var resultMap = mutableMapOf<String, String>()
        var listOfAppUsers = appUserRepository.findAll()
        listOfAppUsers.forEach { appUser: AppUser ->
            run {
                resultMap[appUser.username] = "${appUser.firstName} ${appUser.lastName}"
            }
        }
        return resultMap
    }

    @GetMapping("/find address/{username}")
    fun findUserAddress(@PathVariable username: String): List<AppUserAddress> {
        if (userDetailsService.userDetails.username.equals(username)) {
            return appUserAddressRepository.findAppUserAddressByAppUser_Username(username)
        } else {
            throw UserMisMatchException(exceptionMessage = "You don't have the access to view the address of user $username")
        }
    }

    @PostMapping("/add address/{username}")
    fun addUserAddress(@PathVariable username: String, @RequestBody address: String): ResponseEntity<String> {
        if (userDetailsService.userDetails.username.equals(username)) {
            var user = appUserRepository.findByUsername(username)
            user.address = user.address + AppUserAddress(address = address, appUser = user)
            appUserRepository.save(user)
            return ResponseEntity("Address added successfully for username $username", HttpStatus.ACCEPTED)
        } else {
            throw UserMisMatchException(exceptionMessage = "You don't have the access to view the address of user $username")
        }
    }

    @DeleteMapping("delete address/{username}")
    @Transactional
    fun deleteUserAddress(@PathVariable username: String, @RequestBody address: String): ResponseEntity<String> {
        if (userDetailsService.userDetails.username.equals(username)) {
            appUserAddressRepository.deleteAppUserAddressByAddressAndAppUser_Username(
                address = address,
                userName = username
            )
            return ResponseEntity("Address deleted successfully from username $username", HttpStatus.ACCEPTED)
        } else {
            throw UserMisMatchException(exceptionMessage = "You don't have the access to view the address of user $username")
        }
    }

    @PostMapping("/adduser")
    fun addUser(@RequestBody userData: AppUserData): AppUser {
        userData.password = passwordEncoder.encode(userData.password)
        val user = AppUser(
            username = userData.username,
            firstName = userData.firstName,
            password = userData.password,
            lastName = userData.lastName,
            role = userData.role
        )
        var listOfAddress = ArrayList<AppUserAddress>()
        userData.address.forEach { address ->
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

    @DeleteMapping("/delete user/{username}")
    @Transactional
    fun deleteUser(@PathVariable("username") username: String): ResponseEntity<String> {
        var noOfDeletedUser: Long = appUserRepository.deleteByUsername(username)
        if (noOfDeletedUser.toInt() == 0) {
            return ResponseEntity.ok().body("User with $username not found")
        }
        return ResponseEntity.ok().body("$username deleted successfully")
    }

}