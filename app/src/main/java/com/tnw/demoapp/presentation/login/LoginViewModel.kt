package com.tnw.demoapp.presentation.login

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tnw.demoapp.R
import com.tnw.demoapp.data.local.AuthPreferencesRepository
import com.tnw.demoapp.domain.repository.AuthenticationRepository
import com.tnw.demoapp.domain.usecase.LoginInputError
import com.tnw.demoapp.domain.usecase.LoginInputValidator
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isAuthenticated: Boolean = false,
    @param:StringRes val errorMessageRes: Int? = null
)

class LoginViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val authPreferences: AuthPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        LoginUiState(isAuthenticated = authPreferences.isAuthenticated)
    )
    val uiState = _uiState.asStateFlow()

    fun login(username: String, password: String) {
        if (_uiState.value.isLoading) return

        val validationError = LoginInputValidator.validate(username, password)
        if (validationError != null) {
            _uiState.value = LoginUiState(errorMessageRes = validationError.messageRes())
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)
            try {
                if (authenticationRepository.authenticate(username, password)) {
                    authPreferences.isAuthenticated = true
                    _uiState.value = LoginUiState(
                        isSuccess = true,
                        isAuthenticated = true
                    )
                } else {
                    _uiState.value = LoginUiState(
                        errorMessageRes = R.string.error_incorrect_credentials
                    )
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.value = LoginUiState(
                    errorMessageRes = R.string.error_unable_to_sign_in
                )
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun logout() {
        authPreferences.isAuthenticated = false
        _uiState.value = LoginUiState(isAuthenticated = false)
    }

    fun clearError() {
        if (_uiState.value.errorMessageRes != null) {
            _uiState.value = _uiState.value.copy(errorMessageRes = null)
        }
    }

    private fun LoginInputError.messageRes(): Int = when (this) {
        LoginInputError.MissingUsername -> R.string.error_missing_username
        LoginInputError.MissingPassword -> R.string.error_missing_password
        LoginInputError.InvalidUsername -> R.string.error_invalid_username
    }
}
