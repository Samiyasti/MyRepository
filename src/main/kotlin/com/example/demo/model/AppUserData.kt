package com.example.demo.model

data class AppUserData(
    var username: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var password: String = "",
    var address: List<String> =ArrayList<String>()
)

