package com.example.demo.controller

import com.example.demo.exception.UsernameNotPresentException
import com.example.demo.model.AppStoreData
import com.example.demo.model.UserStore
import com.example.demo.repository.AppUserRepository
import com.example.demo.repository.UserStoreRepository
import com.example.demo.service.AppUserDetailsService
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.transaction.Transactional

@RestController
@RequestMapping("/store")
class StoreController {
    @Autowired
    lateinit var userStoreRepository: UserStoreRepository

    @Autowired
    lateinit var appUserRepository: AppUserRepository

    @Autowired
    lateinit var userDetailsService: AppUserDetailsService

    @PostMapping("/add store")
    fun addStore(@RequestBody appStoreData: AppStoreData): ResponseEntity<String> {
        try {
            var user = appUserRepository.findByUsername(appStoreData.username)
            var store = UserStore(storeName = appStoreData.storeName, storeType = appStoreData.storeType)
            userStoreRepository.save(store)
            store.user.add(user)
            user.store.add(store)
            appUserRepository.saveAndFlush(user)

            return ResponseEntity(
                "Store ${appStoreData.storeName} added successfully for user ${appStoreData.username}",
                HttpStatus.ACCEPTED
            )
        } catch (ex: EmptyResultDataAccessException) {
            throw UsernameNotPresentException(exceptionMessage = "${appStoreData.username} not found")
        }
    }

    @DeleteMapping("delete store/{username}/{storename}")
    @Transactional
    fun deleteStore(
        @PathVariable("username") username: String, @PathVariable("storename") storename: String
    ): ResponseEntity<String> {
        var store =
            userStoreRepository.findUserStoreByStoreNameAndUser(storename, appUserRepository.findByUsername(username))
        userStoreRepository.deleteUserStoreByStoreNameAndAndUser(storename,appUserRepository.findByUsername(username))
        return ResponseEntity("$storename for user $username has been deleted successfully", HttpStatus.ACCEPTED)
    }
}