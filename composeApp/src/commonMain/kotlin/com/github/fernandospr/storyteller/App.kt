package com.github.fernandospr.storyteller

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.github.fernandospr.storyteller.data.CharacterRepository
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
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun App(textToSpeech: TextToSpeech) {
    val storyTellerRepository = GeminiStoryTellerRepository(stringResource(MR.strings.prompt))
    val characterRepository = CharacterRepository()

    PreComposeApp {
        val navigator = rememberNavigator()

        MaterialTheme {
            NavHost(
                navigator = navigator,
                initialRoute = "/character-selection"
            ) {
                scene("/character-selection") {
                    CharacterSelectionScreen(
                        characterRepository.getAllCharacters()
                    ) { character ->
                        navigator.navigate("/story/${character.name}")
                    }
                }
                scene("/story/{characterName}") { backStackEntry ->
                    val name = checkNotNull(backStackEntry.path<String>("characterName"))
                    val character = characterRepository.getCharacter(name)

                    val storyTellerViewModel = getViewModel(Unit, viewModelFactory {
                        StoryTellerViewModel(character, storyTellerRepository, textToSpeech)
                    })
                    val uiState by storyTellerViewModel.uiState.collectAsState()

                    LaunchedEffect(Unit) {
                        storyTellerViewModel.newStory()
                    }

                    AnimatedContent(targetState = uiState) { targetState ->
                        when (targetState) {
                            is StoryTellerUiState.LoadingStory -> LoadingStoryScreen(
                                character.uiDescription,
                                onBackClick = navigator::popBackStack
                            )

                            is StoryTellerUiState.ErrorLoadingStory -> ErrorLoadingStoryScreen(
                                character.uiDescription,
                                onBackClick = navigator::popBackStack,
                                onRetryClick = storyTellerViewModel::newStory
                            )

                            is StoryTellerUiState.Story -> {
                                var isPlaying by rememberSaveable { mutableStateOf(false) }
                                StoryScreen(
                                    character.uiDescription,
                                    character.backgroundColor,
                                    targetState.story,
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
}
