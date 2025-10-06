package com.dekisolutions.realestatelisting.data.repository

import com.dekisolutions.realestatelisting.data.api.PropertyApiService
import com.dekisolutions.realestatelisting.data.mapper.toDomain
import com.dekisolutions.realestatelisting.data.model.ListingItem
import com.dekisolutions.realestatelisting.data.model.ListingsResponse
import com.dekisolutions.realestatelisting.domain.model.Property
import com.dekisolutions.realestatelisting.domain.contracts.PropertyRepository
import retrofit2.Response

class PropertyRepositoryImpl(
    private val apiService: PropertyApiService
) : PropertyRepository {

    override suspend fun getAllProperties(): Result<List<Property>> {
        return try {
            val response: Response<ListingsResponse> = apiService.getAllListings()
            if (response.isSuccessful) {
                val listingsResponse = response.body()
                if (listingsResponse != null) {
                    Result.success(listingsResponse.items.toDomain())
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Failed to fetch properties: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPropertyById(id: String): Result<Property> {
        return try {
            val response: Response<ListingItem> = apiService.getListingById(id)
            if (response.isSuccessful) {
                val listing = response.body()
                if (listing != null) {
                    Result.success(listing.toDomain())
                } else {
                    Result.failure(Exception("Property not found"))
                }
            } else {
                Result.failure(Exception("Failed to fetch property: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
