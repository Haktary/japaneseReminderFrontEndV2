package com.myapp.ui.login

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

sealed class LoginEvent {
    data object NavigateToHome : LoginEvent()
    data object NavigateToRegister : LoginEvent()
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email, error = null)
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(password = password, error = null)
    }

    fun login() {
        val state = _uiState.value
        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(error = "Email and password are required")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = authRepository.login(state.email, state.password)
            if (result.isSuccess) {
                _event.emit(LoginEvent.NavigateToHome)
            } else {
                val errorMessage = when (result) {
                    is com.myapp.core.Result.Error -> {
                        val raw = result.message ?: result.exception.message ?: "Login failed"
                        if (raw.startsWith("{\"detail\":")) {
                            raw.substringAfter("\"detail\":\"").substringBefore("\"}").replace("\\n", "\n")
                        } else {
                            raw
                        }
                    }
                    else -> "Login failed"
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = errorMessage,
                )
            }
        }
    }

    fun navigateToRegister() {
        viewModelScope.launch {
            _event.emit(LoginEvent.NavigateToRegister)
        }
    }
}
