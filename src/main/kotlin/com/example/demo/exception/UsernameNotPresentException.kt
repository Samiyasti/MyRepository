package com.example.demo.exception

import org.springframework.dao.EmptyResultDataAccessException

class UsernameNotPresentException(
    var exceptionMessage: String=""
) : EmptyResultDataAccessException(0)