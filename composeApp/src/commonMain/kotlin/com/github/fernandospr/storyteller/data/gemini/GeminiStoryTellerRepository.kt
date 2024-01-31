package com.github.fernandospr.storyteller.data.gemini

import StoryTeller.composeApp.BuildConfig
import com.github.fernandospr.storyteller.data.StoryTellerRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class GeminiStoryTellerRepository : StoryTellerRepository {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun getStory(promptPlaceholder: String, character: String): String {
        val prompt = promptPlaceholder.replace("%s", character)

        val response = httpClient.post(
            "https://generativelanguage.googleapis.com" +
                    "/v1beta/models/gemini-pro:generateContent?" +
                    "key=${BuildConfig.GEMINI_API_KEY}"
        ) {
            setBody(AiRequest(listOf(Content(listOf(Part(prompt))))))
            contentType(ContentType.Application.Json)
        }.body<AiResponse>()
        return response.candidates.first().content.parts.first().text
    }

    override fun clear() {
        httpClient.close()
    }
}