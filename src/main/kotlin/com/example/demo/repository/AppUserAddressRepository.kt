package com.example.demo.repository

import com.example.demo.model.AppUserAddress
import org.springframework.data.jpa.repository.JpaRepository

interface AppUserAddressRepository :JpaRepository<AppUserAddress,String> {
    fun findAppUserAddressByAppUser_Username(userName: String)
}