package com.dekisolutions.realestatelisting.vw

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.dekisolutions.realestatelisting.domain.model.Property
import com.dekisolutions.realestatelisting.domain.usecase.GetPropertyByIdUseCase
import com.dekisolutions.realestatelisting.navigation.DetailScreenRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPropertyByIdUseCase: GetPropertyByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val detailRoute: DetailScreenRoute = savedStateHandle.toRoute()
    private val propertyId: String = detailRoute.propertyId

    init {
        loadProperty()
    }

    private fun loadProperty() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            getPropertyByIdUseCase(propertyId)
                .onSuccess { property ->
                    _uiState.value = _uiState.value.copy(
                        property = property,
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
}

data class DetailUiState(
    val property: Property? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
