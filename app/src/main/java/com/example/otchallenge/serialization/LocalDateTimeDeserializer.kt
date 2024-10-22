package com.example.otchallenge.serialization

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class LocalDateTimeDeserializer: JsonDeserializer<LocalDateTime> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime? {
        // ** Converts the JSON ISO format date into local date time, by considering time zone.
        // ** So, even if the date time is in different time zone, it will be converted into local time zone.

        val zonedDateTime = DateTimeFormatter.ISO_DATE_TIME.parse(json!!.asString, ZonedDateTime::from)
        return zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
    }
}