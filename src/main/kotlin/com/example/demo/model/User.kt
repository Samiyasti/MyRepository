package com.example.demo.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Users")
data class User (
    @Id
   // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Column(unique = true)
    var userName:String="",
    var firstName:String="",
    var lastName:String="",
    var password:String="default",
    var role:String="User"
){
    constructor(userName: String,firstName: String,lastName: String,password: String,role: String) : this(UUID.randomUUID().toString(),userName, firstName, lastName, password, role)

}