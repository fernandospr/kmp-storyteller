package com.github.fernandospr.storyteller

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.github.fernandospr.storyteller.data.gemini.GeminiStoryTellerRepository
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun App(speak: (String) -> Unit) {
    MaterialTheme {
        val prompt = stringResource(MR.strings.prompt)
        val storyTellerViewModel = getViewModel(Unit, viewModelFactory {
            StoryTellerViewModel(GeminiStoryTellerRepository(prompt))
        })
        val uiState by storyTellerViewModel.uiState.collectAsState()
        val characters = getCharacters()

        when (val state = uiState) {
            is StoryTellerUiState.LoadingStory -> LoadingStoryScreen(state.uiDescription)

            is StoryTellerUiState.Story -> StoryScreen(
                state.uiDescription,
                state.story,
                onPlayClick = { speak(it) },
                onResetClick = storyTellerViewModel::reset
            )

            is StoryTellerUiState.CharacterSelection -> CharacterSelectionScreen(
                characters
            ) { character ->
                storyTellerViewModel.newStory(character)
            }

            is StoryTellerUiState.ErrorLoadingStory -> ErrorLoadingStoryScreen(state.uiDescription) {
                storyTellerViewModel.reset()
            }
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
