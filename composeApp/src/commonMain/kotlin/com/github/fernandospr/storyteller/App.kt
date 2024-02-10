package com.github.fernandospr.storyteller

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.query
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun App(textToSpeech: TextToSpeech) {
    val storyTellerRepository = GeminiStoryTellerRepository(stringResource(MR.strings.prompt))
    val characters = getCharacters()

    PreComposeApp {
        val navigator = rememberNavigator()

        MaterialTheme {
            NavHost(
                navigator = navigator,
                initialRoute = "/character-selection"
            ) {
                scene("/character-selection") {
                    CharacterSelectionScreen(
                        characters
                    ) { character ->
                        navigator.navigate("/story/character?name=${character.name}&uiDescription=${character.uiDescription}")
                    }
                }
                scene("/story/{character}") { backStackEntry ->
                    val name = backStackEntry.query<String>("name").orEmpty()
                    val uiDescription = backStackEntry.query<String>("uiDescription").orEmpty()
                    val character = Character(name, uiDescription)

                    val storyTellerViewModel = getViewModel(Unit, viewModelFactory {
                        StoryTellerViewModel(character, storyTellerRepository, textToSpeech)
                    })
                    val uiState by storyTellerViewModel.uiState.collectAsState()

                    LaunchedEffect(Unit) {
                        storyTellerViewModel.newStory()
                    }

                    when (val state = uiState) {
                        is StoryTellerUiState.LoadingStory -> LoadingStoryScreen(
                            state.uiDescription,
                            onBackClick = navigator::popBackStack
                        )

                        is StoryTellerUiState.ErrorLoadingStory -> ErrorLoadingStoryScreen(
                            state.character.uiDescription,
                            onBackClick = navigator::popBackStack,
                            onRetryClick = storyTellerViewModel::newStory
                        )

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
                                onBackClick = navigator::popBackStack
                            )
                        }
                    }
                }
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
