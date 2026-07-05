package com.myapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashEvent {
    data object NavigateToLogin : SplashEvent()
    data object NavigateToHome : SplashEvent()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _event = MutableSharedFlow<SplashEvent>()
    val event: SharedFlow<SplashEvent> = _event

    init {
        viewModelScope.launch {
            delay(500)
            val isLoggedIn = authRepository.isLoggedIn()
            val navigationEvent = if (isLoggedIn) SplashEvent.NavigateToHome else SplashEvent.NavigateToLogin
            _event.emit(navigationEvent)
        }
    }
}
