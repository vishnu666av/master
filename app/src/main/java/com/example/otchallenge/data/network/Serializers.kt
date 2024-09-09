package com.example.otchallenge.data.network

import com.example.otchallenge.data.models.Book
import com.example.otchallenge.data.models.EmptyArray
import com.example.otchallenge.data.models.ResponseData
import com.example.otchallenge.data.models.ResultWithData
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer

object UserListSerializer :
    JsonTransformingSerializer<List<Book>>(ListSerializer(Book.serializer())) {
    // If response is not an array, then it is a single object that should be wrapped into the array
    override fun transformDeserialize(element: JsonElement): JsonElement =
        if (element !is JsonArray) {
            JsonArray(listOf(element))
        } else {
            element
        }
}

object ResponseDataSerializer : KSerializer<ResponseData> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ResponseData", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ResponseData {
        val jsonDecode = decoder as JsonDecoder
        val jsonElement = jsonDecode.decodeJsonElement()
        return when (jsonElement) {
            is JsonArray ->
                EmptyArray

            is JsonObject ->
                ResultWithData(
                    Json { ignoreUnknownKeys = true }.decodeFromJsonElement(
                        UserListSerializer,
                        jsonElement.get("books") ?: JsonArray(emptyList()),
                    ),
                )

            else -> throw IllegalArgumentException("Unknown JSON element")
        }
    }

    override fun serialize(
        encoder: Encoder,
        value: ResponseData,
    ) {
        when (value) {
            EmptyArray -> encoder.encodeString(Json.encodeToString(emptyList<Book>()))
            is ResultWithData ->
                encoder.encodeString(
                    Json.encodeToString(
                        UserListSerializer,
                        value.books,
                    ),
                )
        }
    }
}
