package com.tnw.demoapp

import com.tnw.demoapp.domain.usecase.LoginInputError
import com.tnw.demoapp.domain.usecase.LoginInputValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LoginInputValidatorTest {
    @Test
    fun returnsMissingUsernameWhenUsernameIsBlank() {
        assertEquals(
            LoginInputError.MissingUsername,
            LoginInputValidator.validate("  ", "password123")
        )
    }

    @Test
    fun returnsMissingPasswordWhenPasswordIsBlank() {
        assertEquals(
            LoginInputError.MissingPassword,
            LoginInputValidator.validate("demo@example.com", "\t")
        )
    }

    @Test
    fun returnsInvalidUsernameWhenUsernameDoesNotContainAtSign() {
        assertEquals(
            LoginInputError.InvalidUsername,
            LoginInputValidator.validate("demo.example.com", "password123")
        )
    }

    @Test
    fun returnsNoErrorForValidInput() {
        assertNull(LoginInputValidator.validate("demo@example.com", "password123"))
    }
}
