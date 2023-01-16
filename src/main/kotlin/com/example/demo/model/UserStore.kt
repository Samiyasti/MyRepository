package com.example.demo.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity(name = "app_user_store")
data class UserStore(
    @Id
    var id: String = UUID.randomUUID().toString(),
    @Column(nullable = false)
    var storeName: String = "",
    @Column(nullable = false)
    var storeType: String = "",
    @JsonIgnore
    @ManyToMany(
        mappedBy = "store",
        fetch = FetchType.EAGER,
        cascade = [CascadeType.ALL]
    )
    var user: MutableList<AppUser> = ArrayList()
)