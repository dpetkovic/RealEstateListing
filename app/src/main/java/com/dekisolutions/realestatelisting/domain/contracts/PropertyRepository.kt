package com.dekisolutions.realestatelisting.domain.contracts

import com.dekisolutions.realestatelisting.domain.model.Property

interface PropertyRepository {
    suspend fun getAllProperties(): Result<List<Property>>
    suspend fun getPropertyById(id: String): Result<Property>
}
