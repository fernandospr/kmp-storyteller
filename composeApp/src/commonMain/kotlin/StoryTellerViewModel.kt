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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

data class StoryTellerUiState(val story: String)

class StoryTellerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(StoryTellerUiState("..."))
    val uiState = _uiState.asStateFlow()

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    fun newStory(animal: String) {
        viewModelScope.launch {
            val story = getStory(animal)
            _uiState.update {
                it.copy(story = story)
            }
        }
    }

    private suspend fun getStory(animal: String): String {
        val prompt = "Write a bedtime story of less than 100 words for children with a " +
                "$animal, it should start with Once upon a time"

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