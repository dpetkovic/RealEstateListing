package com.dekisolutions.realestatelisting.domain.usecase

import com.dekisolutions.realestatelisting.domain.model.Property
import com.dekisolutions.realestatelisting.domain.contracts.PropertyRepository

class GetAllPropertiesUseCase(
    private val propertyRepository: PropertyRepository
) {
    suspend operator fun invoke(): Result<List<Property>> {
        return propertyRepository.getAllProperties()
    }
}
