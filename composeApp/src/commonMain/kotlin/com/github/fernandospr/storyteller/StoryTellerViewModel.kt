package com.github.fernandospr.storyteller

import com.github.fernandospr.storyteller.data.Character
import com.github.fernandospr.storyteller.data.StoryTellerRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class StoryTellerUiState {
    data class LoadingStory(val uiDescription: String) : StoryTellerUiState()
    data class Story(val uiDescription: String, val story: String) : StoryTellerUiState()
    data class ErrorLoadingStory(val character: Character) : StoryTellerUiState()
}

class StoryTellerViewModel(
    private val character: Character,
    private val repository: StoryTellerRepository,
    private val textToSpeech: TextToSpeech
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<StoryTellerUiState>(StoryTellerUiState.LoadingStory(character.uiDescription))
    val uiState = _uiState.asStateFlow()

    fun newStory() {
        textToSpeech.speak(character.name) {}
        _uiState.value = StoryTellerUiState.LoadingStory(character.uiDescription)
        viewModelScope.launch {
            try {
                val story = repository.getStory(character.name)
                _uiState.value = StoryTellerUiState.Story(character.uiDescription, story)
            } catch (ex: Exception) {
                if (ex !is CancellationException) {
                    _uiState.value = StoryTellerUiState.ErrorLoadingStory(character)
                }
            }
        }
    }

    fun speak(text: String, onComplete: () -> Unit) {
        textToSpeech.speak(text, onComplete)
    }

    fun stopSpeaking() {
        textToSpeech.stopSpeaking()
    }

    override fun onCleared() {
        stopSpeaking()
    }
}