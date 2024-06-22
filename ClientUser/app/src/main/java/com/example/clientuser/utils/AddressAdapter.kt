package com.example.clientuser.utils

import com.example.clientuser.model.Address
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

object AddressAdapter: JsonAdapter<Address>() {
    @FromJson
    override fun fromJson(reader: JsonReader): Address {
        var street: String? = null
        var city: String? = null
        var civicNumber: String? = null
        var zipCode: String? = null
        var state: String? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "street" -> street = reader.nextString()
                "city" -> city = reader.nextString()
                "civicNumber" -> civicNumber = reader.nextString()
                "zipCode" -> zipCode = reader.nextString()
                "state" -> state = reader.nextString()
                else -> reader.skipValue()
            }
        }
        reader.endObject()
        return Address(street ?: "", city ?: "", civicNumber ?: "", zipCode ?: "", state ?: "")
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Address?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
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