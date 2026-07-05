package com.myapp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RegisterEvent {
    data object NavigateToHome : RegisterEvent()
    data object NavigateBack : RegisterEvent()
}

data class RegisterUiState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<RegisterEvent>()
    val event: SharedFlow<RegisterEvent> = _event.asSharedFlow()

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email, error = null)
    }

    fun onUsernameChanged(username: String) {
        _uiState.value = _uiState.value.copy(username = username, error = null)
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(password = password, error = null)
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword, error = null)
    }

    fun register() {
        val state = _uiState.value

        if (state.email.isBlank() || state.username.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(error = "All fields are required")
            return
        }

        if (state.password != state.confirmPassword) {
            _uiState.value = state.copy(error = "Passwords do not match")
            return
        }

        if (state.password.length < 6) {
            _uiState.value = state.copy(error = "Password must be at least 6 characters")
            return
        }

        if (state.password.length > 50) {
            _uiState.value = state.copy(error = "Password must be 50 characters or less")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = authRepository.register(
                email = state.email,
                username = state.username,
                password = state.password,
            )
            if (result.isSuccess) {
                _event.emit(RegisterEvent.NavigateToHome)
            } else {
                val errorMessage = when (result) {
                    is com.myapp.core.Result.Error -> {
                        val raw = result.message ?: result.exception.message ?: "Registration failed"
                        if (raw.startsWith("{\"detail\":")) {
                            raw.substringAfter("\"detail\":\"").substringBefore("\"}").replace("\\n", "\n")
                        } else {
                            raw
                        }
                    }
                    else -> "Registration failed"
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = errorMessage,
                )
            }
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _event.emit(RegisterEvent.NavigateBack)
        }
    }
}
