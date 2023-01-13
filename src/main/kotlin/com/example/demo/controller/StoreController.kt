package com.example.demo.controller

import com.example.demo.exception.UsernameNotPresentException
import com.example.demo.model.AppStoreData
import com.example.demo.model.UserStore
import com.example.demo.repository.AppUserRepository
import com.example.demo.repository.UserStoreRepository
import com.example.demo.service.AppUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
            var store=UserStore(storeName = appStoreData.storeName, storeType = appStoreData.storeType)
            user.store + store
            appUserRepository.save(user)
            return ResponseEntity(
                "Store ${appStoreData.storeName} added successfully for user ${appStoreData.username}",
                HttpStatus.ACCEPTED
            )
        } catch (ex: EmptyResultDataAccessException) {
            throw UsernameNotPresentException(exceptionMessage = "${appStoreData.username} not found")
        }

    }
}