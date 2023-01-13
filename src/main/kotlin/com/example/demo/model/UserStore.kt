package com.example.demo.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity(name = "app_user_store")
data class UserStore (
    @Id
    var id:String= UUID.randomUUID().toString(),
    @Column(nullable = false)
    var storeName:String="",
    @Column(nullable = false)
    var storeType:String="",
    @ManyToMany(mappedBy = "store")
    @Column(nullable = false)
    var user: List<AppUser> = ArrayList()
)