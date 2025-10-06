package com.dekisolutions.realestatelisting.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListingsResponse(
    @Json(name = "items") val items: List<ListingItem>,
    @Json(name = "totalCount") val totalCount: Int
)

@JsonClass(generateAdapter = true)
data class ListingItem(
    @Json(name = "id") val id: Int,
    @Json(name = "bedrooms") val bedrooms: Int?,
    @Json(name = "city") val city: String,
    @Json(name = "area") val area: Double,
    @Json(name = "url") val url: String?,
    @Json(name = "price") val price: Double,
    @Json(name = "professional") val professional: String,
    @Json(name = "propertyType") val propertyType: String,
    @Json(name = "offerType") val offerType: Int,
    @Json(name = "rooms") val rooms: Int?
)
