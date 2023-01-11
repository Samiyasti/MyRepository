package com.example.demo.repository

import com.example.demo.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MyRepository:JpaRepository<User,Long> {
    @Query(value = "select u from User u where u .firstName like ?1%")
    fun findUserByFirstName(firstName: String):List<User>
    @Query("select u from User u where u.userName like ?1")
    fun findByUserName(userName:String):User
    fun findById(id:String):User
}