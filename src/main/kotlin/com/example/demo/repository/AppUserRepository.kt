package com.example.demo.repository

import com.example.demo.model.AppUser
import org.springframework.data.jpa.repository.JpaRepository

interface AppUserRepository : JpaRepository<AppUser, String> {

    fun findByUsername(userName: String): AppUser

    fun deleteByUsername(userName: String): Long
}
