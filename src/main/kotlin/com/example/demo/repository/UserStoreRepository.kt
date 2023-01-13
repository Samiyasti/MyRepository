package com.example.demo.repository

import com.example.demo.model.AppUser
import com.example.demo.model.UserStore
import org.springframework.data.jpa.repository.JpaRepository

interface UserStoreRepository : JpaRepository<UserStore, String> {
    fun findUserStoresByStoreType(storeType: String): List<UserStore>
    fun findUserStoresByUser(user:AppUser):List<UserStore>
}