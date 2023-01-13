package com.example.demo.repository

import com.example.demo.model.AppUserAddress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface AppUserAddressRepository :JpaRepository<AppUserAddress,String> {
    fun findAppUserAddressByAppUser_Username(userName: String):List<AppUserAddress>

    @Modifying
    @Query("delete from app_user_address a_a where a_a.address=?1 and a_a.appUser=(select a_u from app_users a_u where a_u.username=?2)")
    fun deleteAppUserAddressByAddressAndAppUser_Username(address:String,userName: String)
}