package com.example.demo.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity(name = "app_user_address")
data class AppUserAddress(
    @Id
    var id: String = UUID.randomUUID().toString(),
    @Column(nullable = false, columnDefinition = "VARCHAR(1000)")
    var address: String = "",
    @JsonIgnore
    @ManyToOne
    var appUser: AppUser = AppUser()
)


