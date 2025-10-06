package com.dekisolutions.realestatelisting.domain.usecase

import com.dekisolutions.realestatelisting.domain.model.Property
import com.dekisolutions.realestatelisting.domain.contracts.PropertyRepository

class GetPropertyByIdUseCase(
    private val propertyRepository: PropertyRepository
) {
    suspend operator fun invoke(id: String): Result<Property> {
        return propertyRepository.getPropertyById(id)
    }
}
