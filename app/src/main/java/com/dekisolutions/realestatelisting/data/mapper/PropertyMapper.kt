package com.dekisolutions.realestatelisting.data.mapper

import com.dekisolutions.realestatelisting.data.model.ListingItem
import com.dekisolutions.realestatelisting.domain.model.Property
import com.dekisolutions.realestatelisting.domain.model.PropertyType

fun ListingItem.toDomain(): Property {
    return Property(
        id = this.id.toString(),
        title = generateTitle(),
        description = generateDescription(),
        price = this.price,
        address = this.city,
        imageUrl = this.url ?: "",
        bedrooms = this.bedrooms ?: 0,
        area = this.area,
        type = mapPropertyType(this.propertyType)
    )
}

fun List<ListingItem>.toDomain(): List<Property> {
    return this.map { it.toDomain() }
}

private fun ListingItem.generateTitle(): String {
    return when {
        bedrooms != null -> "$propertyType - $bedrooms bedroom${if (bedrooms > 1) "s" else ""}"
        else -> propertyType
    }
}

private fun ListingItem.generateDescription(): String {
    val parts = mutableListOf<String>()

    if (rooms != null) {
        parts.add("$rooms room${if (rooms > 1) "s" else ""}")
    }

    parts.add("${area.toInt()} m²")
    parts.add("Located in $city")
    parts.add("Listed by $professional")

    return parts.joinToString(" • ")
}

private fun mapPropertyType(propertyType: String): PropertyType {
    return when (propertyType.lowercase()) {
        "maison - villa", "maison", "villa" -> PropertyType.HOUSE
        "appartement", "apartment" -> PropertyType.APARTMENT
        "condo" -> PropertyType.CONDO
        "townhouse", "maison de ville" -> PropertyType.TOWNHOUSE
        else -> PropertyType.HOUSE // Default fallback
    }
}
