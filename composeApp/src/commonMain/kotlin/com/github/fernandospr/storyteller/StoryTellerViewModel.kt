package com.github.fernandospr.storyteller

import StoryTeller.composeApp.BuildConfig
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

sealed class StoryTellerUiState {
    data class LoadingStory(val uiDescription: String) : StoryTellerUiState()
    data object CharacterSelection : StoryTellerUiState()
    data class Story(val uiDescription: String, val story: String) : StoryTellerUiState()
}

class StoryTellerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<StoryTellerUiState>(StoryTellerUiState.CharacterSelection)
    val uiState = _uiState.asStateFlow()

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    fun newStory(character: Character) {
        viewModelScope.launch {
            _uiState.value = StoryTellerUiState.LoadingStory(character.uiDescription)
            val story = getStory(character.name)
            _uiState.value = StoryTellerUiState.Story(character.uiDescription, story)
        }
    }

    fun reset() {
        _uiState.value = StoryTellerUiState.CharacterSelection
    }

    private suspend fun getStory(character: String): String {
        val prompt = "Write a bedtime story of less than 100 words for children with a " +
                "$character, it should start with Once upon a time"

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

    override fun onCleared() {
        httpClient.close()
    }
}