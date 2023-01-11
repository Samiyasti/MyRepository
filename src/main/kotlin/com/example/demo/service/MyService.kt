package com.example.demo.service

import com.example.demo.model.User
import com.example.demo.repository.MyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MyService {

    @Autowired
    private lateinit var repo: MyRepository

    fun findUser(firstName: String): List<User> {

        return repo.findUserByFirstName(firstName)
    }

    fun addUser(user: User) {
        repo.save(user)
    }

    fun getUser(user: User): User {
        return repo.findById(user.id)
    }

    fun deleteUser(userName: String) {
        repo.delete(repo.findByUserName(userName))
    }

    fun findAllUser():List<User>{
        return repo.findAll()
    }
}