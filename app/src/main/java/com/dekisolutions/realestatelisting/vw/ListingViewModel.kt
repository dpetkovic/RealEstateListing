package com.dekisolutions.realestatelisting.vw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dekisolutions.realestatelisting.domain.model.Property
import com.dekisolutions.realestatelisting.domain.usecase.GetAllPropertiesUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ListingViewModel(
    private val getAllPropertiesUseCase: GetAllPropertiesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListingUiState())
    val uiState: StateFlow<ListingUiState> = _uiState.asStateFlow()

    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

    init {
        loadProperties()
    }

    fun loadProperties() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            getAllPropertiesUseCase()
                .onSuccess { properties ->
                    _uiState.value = _uiState.value.copy(
                        properties = properties,
                        isLoading = false,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Unknown error occurred"
                    )
                }
        }
    }

    fun onPropertyClick(propertyId: String) {
        viewModelScope.launch {
            _navigationEvents.send(NavigationEvent.NavigateToDetail(propertyId))
        }
    }
}

data class ListingUiState(
    val properties: List<Property> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class NavigationEvent {
    data class NavigateToDetail(val propertyId: String) : NavigationEvent()
}
