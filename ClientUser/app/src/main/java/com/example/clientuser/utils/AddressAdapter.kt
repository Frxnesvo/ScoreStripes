package com.example.clientuser.utils

import com.example.clientuser.model.Address
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

object AddressAdapter {

    private val NAME_OPTIONS = JsonReader.Options.of(
        "street", "city", "civicNumber", "zipCode", "state", "defaultAddress"
    )

    @FromJson
    fun fromJson(reader: JsonReader): Address? {
        var street: String? = null
        var city: String? = null
        var civicNumber: String? = null
        var zipCode: String? = null
        var state: String? = null
        var defaultAddress: Boolean = false

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(NAME_OPTIONS)) {
                0 -> street = reader.nextString()
                1 -> city = reader.nextString()
                2 -> civicNumber = reader.nextString()
                3 -> zipCode = reader.nextString()
                4 -> state = reader.nextString()
                5 -> defaultAddress = reader.nextBoolean()
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        // Usa le funzioni di validazione definite nella classe Address
        if (street == null || !Address.validateStreet(street)) {
            throw JsonDataException("Invalid or missing street")
        }
        if (city == null || !Address.validateCity(city)) {
            throw JsonDataException("Invalid or missing city")
        }
        if (civicNumber == null || !Address.validateCivicNumber(civicNumber)) {
            throw JsonDataException("Invalid or missing civic number")
        }
        if (zipCode == null || !Address.validateZipCode(zipCode)) {
            throw JsonDataException("Invalid or missing zip code")
        }
        if (state == null || !Address.validateState(state)) {
            throw JsonDataException("Invalid or missing state")
        }

        return Address(
            street = street,
            city = city,
            civicNumber = civicNumber,
            zipCode = zipCode,
            state = state,
            defaultAddress = defaultAddress
        )
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: Address?) {
        if (value == null) {
            writer.nullValue()
            return
        }
        writer.beginObject()
        writer.name("street").value(value.street)
        writer.name("city").value(value.city)
        writer.name("civicNumber").value(value.civicNumber)
        writer.name("zipCode").value(value.zipCode)
        writer.name("state").value(value.state)
        writer.name("defaultAddress").value(value.defaultAddress)
        writer.endObject()
    }
}