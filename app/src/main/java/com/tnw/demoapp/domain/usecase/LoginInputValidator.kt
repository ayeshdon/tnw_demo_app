package com.tnw.demoapp.domain.usecase

sealed interface LoginInputError {
    data object MissingUsername : LoginInputError
    data object MissingPassword : LoginInputError
    data object InvalidUsername : LoginInputError
}

object LoginInputValidator {
    fun validate(username: String, password: String): LoginInputError? = when {
        username.isBlank() -> LoginInputError.MissingUsername
        password.isBlank() -> LoginInputError.MissingPassword
        !username.contains("@") -> LoginInputError.InvalidUsername
        else -> null
    }
}
