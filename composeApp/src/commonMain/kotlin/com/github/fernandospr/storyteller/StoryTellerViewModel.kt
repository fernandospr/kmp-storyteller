package com.github.fernandospr.storyteller

import com.github.fernandospr.storyteller.data.StoryTellerRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class StoryTellerUiState {
    data class LoadingStory(val uiDescription: String) : StoryTellerUiState()
    data object CharacterSelection : StoryTellerUiState()
    data class Story(val uiDescription: String, val story: String) : StoryTellerUiState()
    data class ErrorLoadingStory(val character: Character) : StoryTellerUiState()
}

class StoryTellerViewModel(
    private val repository: StoryTellerRepository,
    private val textToSpeech: TextToSpeech
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<StoryTellerUiState>(StoryTellerUiState.CharacterSelection)
    val uiState = _uiState.asStateFlow()

    fun newStory(character: Character) {
        viewModelScope.launch {
            _uiState.value = StoryTellerUiState.LoadingStory(character.uiDescription)
            try {
                val story = repository.getStory(character.name)
                _uiState.value = StoryTellerUiState.Story(character.uiDescription, story)
            } catch (ex: Exception) {
                _uiState.value = StoryTellerUiState.ErrorLoadingStory(character)
            }
        }
    }

    fun reset() {
        stopSpeaking()
        _uiState.value = StoryTellerUiState.CharacterSelection
    }

    override fun onCleared() {
        stopSpeaking()
        repository.clear()
    }

    fun speak(text: String, onComplete: () -> Unit) {
        textToSpeech.speak(text, onComplete)
    }

    fun stopSpeaking() {
        textToSpeech.stopSpeaking()
    }
}