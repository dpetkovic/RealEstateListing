package com.dekisolutions.realestatelisting.data.api

import com.dekisolutions.realestatelisting.data.model.ListingItem
import com.dekisolutions.realestatelisting.data.model.ListingsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PropertyApiService {

    @GET("listings.json")
    suspend fun getAllListings(): Response<ListingsResponse>

    @GET("listings/{listingId}.json")
    suspend fun getListingById(@Path("listingId") listingId: String): Response<ListingItem>
}
