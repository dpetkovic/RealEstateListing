package com.dekisolutions.realestatelisting.domain.model

data class Property(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val address: String,
    val imageUrl: String,
    val bedrooms: Int,
    val area: Double,
    val type: PropertyType
)

enum class PropertyType {
    HOUSE,
    APARTMENT,
    CONDO,
    TOWNHOUSE
}
