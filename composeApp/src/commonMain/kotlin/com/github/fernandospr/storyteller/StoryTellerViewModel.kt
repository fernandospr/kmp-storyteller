package com.github.fernandospr.storyteller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.fernandospr.storyteller.data.Character
import com.github.fernandospr.storyteller.data.StoryTellerRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class StoryTellerUiState {
    data object LoadingStory : StoryTellerUiState()
    data class Story(val story: String) : StoryTellerUiState()
    data object ErrorLoadingStory : StoryTellerUiState()
}

class StoryTellerViewModel(
    private val character: Character,
    private val repository: StoryTellerRepository,
    private val textToSpeech: TextToSpeech
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<StoryTellerUiState>(StoryTellerUiState.LoadingStory)
    val uiState = _uiState.asStateFlow()

    fun newStory() {
        textToSpeech.speak(character.name) {}
        _uiState.value = StoryTellerUiState.LoadingStory
        viewModelScope.launch {
            try {
                val story = repository.getStory(character.name)
                _uiState.value = StoryTellerUiState.Story(story)
            } catch (ex: Exception) {
                if (ex !is CancellationException) {
                    _uiState.value = StoryTellerUiState.ErrorLoadingStory
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