package com.example.demo.model

import java.util.*
import javax.persistence.*

@Entity(name = "app_users")
data class AppUser(
    @Id
    var id: String = UUID.randomUUID().toString(),
    @Column(unique = true, nullable = false)
    var username: String = "",
    @Column(nullable = false)
    var firstName: String = "",
    @Column(nullable = false)
    var lastName: String = "",
    @Column(nullable = false)
    var password: String = "",
    @Column(nullable = false)
    var role: String = "USER",
    @OneToMany(mappedBy = "appUser", fetch = FetchType.EAGER)
    var address: List<AppUserAddress> = ArrayList()
)