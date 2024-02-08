package com.github.fernandospr.storyteller

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.github.fernandospr.storyteller.data.gemini.GeminiStoryTellerRepository
import com.github.fernandospr.storyteller.screens.CharacterSelectionScreen
import com.github.fernandospr.storyteller.screens.ErrorLoadingStoryScreen
import com.github.fernandospr.storyteller.screens.LoadingStoryScreen
import com.github.fernandospr.storyteller.screens.StoryScreen
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun App(textToSpeech: TextToSpeech) {
    MaterialTheme {
        val prompt = stringResource(MR.strings.prompt)
        val storyTellerViewModel = getViewModel(Unit, viewModelFactory {
            StoryTellerViewModel(GeminiStoryTellerRepository(prompt), textToSpeech)
        })
        val uiState by storyTellerViewModel.uiState.collectAsState()
        val characters = getCharacters()


        when (val state = uiState) {
            is StoryTellerUiState.LoadingStory -> LoadingStoryScreen(state.uiDescription)

            is StoryTellerUiState.Story -> {
                var isPlaying by rememberSaveable { mutableStateOf(false) }
                StoryScreen(
                    state.uiDescription,
                    state.story,
                    isPlaying = isPlaying,
                    onPlayClick = {
                        storyTellerViewModel.speak(it) { isPlaying = false }
                        isPlaying = true
                    },
                    onStopClick = {
                        storyTellerViewModel.stopSpeaking()
                        isPlaying = false
                    },
                    onResetClick = { storyTellerViewModel.reset() }
                )
            }

            is StoryTellerUiState.CharacterSelection -> CharacterSelectionScreen(
                characters
            ) { character ->
                storyTellerViewModel.newStory(character)
            }

            is StoryTellerUiState.ErrorLoadingStory -> ErrorLoadingStoryScreen(
                state.character.uiDescription,
                onBackClick = storyTellerViewModel::reset,
                onRetryClick = { storyTellerViewModel.newStory(state.character) }
            )
        }
    }
}

@Composable
private fun getCharacters() = listOf(
    Character(name = stringResource(MR.strings.dog_name), uiDescription = "🐶"),
    Character(name = stringResource(MR.strings.cat_name), uiDescription = "🐱"),
    Character(name = stringResource(MR.strings.monkey_name), uiDescription = "🐵"),
    Character(name = stringResource(MR.strings.lion_name), uiDescription = "🦁"),
    Character(name = stringResource(MR.strings.duck_name), uiDescription = "🦆"),
    Character(name = stringResource(MR.strings.rabbit_name), uiDescription = "🐰"),
    Character(name = stringResource(MR.strings.cow_name), uiDescription = "🐮"),
    Character(name = stringResource(MR.strings.pig_name), uiDescription = "🐷"),
    Character(name = stringResource(MR.strings.horse_name), uiDescription = "🐴"),
    Character(name = stringResource(MR.strings.frog_name), uiDescription = "🐸"),
)
