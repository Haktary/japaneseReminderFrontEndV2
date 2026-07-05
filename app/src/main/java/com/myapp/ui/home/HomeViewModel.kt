package com.myapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.domain.model.DashboardStats
import com.myapp.domain.repository.AuthRepository
import com.myapp.domain.repository.ReviewRepository
import com.myapp.domain.repository.SettingsRepository
import com.myapp.domain.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeEvent {
    data object NavigateToLogin : HomeEvent()
}

data class HomeUiState(
    val username: String = "",
    val dashboard: DashboardStats = DashboardStats(),
    val isLoading: Boolean = true,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val reviewRepository: ReviewRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<HomeEvent>()
    val event: SharedFlow<HomeEvent> = _event.asSharedFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val userResult = authRepository.getCurrentUser()
            val username = if (userResult.isSuccess) userResult.getOrNull()?.username ?: "" else ""

            val dashboardResult = reviewRepository.getDashboard()
            val dashboard = if (dashboardResult.isSuccess) {
                dashboardResult.getOrNull() ?: DashboardStats()
            } else {
                DashboardStats()
            }

            _uiState.value = HomeUiState(
                username = username,
                dashboard = dashboard,
                isLoading = false,
            )
        }
    }

    fun refresh() {
        loadData()
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _event.emit(HomeEvent.NavigateToLogin)
        }
    }
}
