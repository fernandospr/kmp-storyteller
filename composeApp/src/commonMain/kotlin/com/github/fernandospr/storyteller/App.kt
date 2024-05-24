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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.fernandospr.storyteller.data.CharacterRepository
import com.github.fernandospr.storyteller.data.gemini.GeminiStoryTellerRepository
import com.github.fernandospr.storyteller.screens.CharacterSelectionScreen
import com.github.fernandospr.storyteller.screens.ErrorLoadingStoryScreen
import com.github.fernandospr.storyteller.screens.LoadingStoryScreen
import com.github.fernandospr.storyteller.screens.StoryScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import storyteller.composeapp.generated.resources.Res
import storyteller.composeapp.generated.resources.prompt

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(textToSpeech: TextToSpeech) {
    val storyTellerRepository = GeminiStoryTellerRepository(stringResource(Res.string.prompt))
    val characterRepository = CharacterRepository()

    val navController = rememberNavController()

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = "/character-selection"
        ) {
            composable("/character-selection") {
                CharacterSelectionScreen(
                    characterRepository.getAllCharacters()
                ) { character ->
                    navController.navigate("/story/${character.name}")
                }
            }
            composable(
                route = "/story/{characterName}",
                arguments = listOf(navArgument("characterName") { type = NavType.StringType })
            ) { backStackEntry ->
                val name = checkNotNull(backStackEntry.arguments?.getString("characterName"))
                val character = characterRepository.getCharacter(name)

                val storyTellerViewModel = viewModel<StoryTellerViewModel>(key = name) {
                    StoryTellerViewModel(character, storyTellerRepository, textToSpeech)
                }
                val uiState by storyTellerViewModel.uiState.collectAsState()

                LaunchedEffect(Unit) {
                    storyTellerViewModel.newStory()
                }

                AnimatedContent(targetState = uiState) { targetState ->
                    when (targetState) {
                        is StoryTellerUiState.LoadingStory -> LoadingStoryScreen(
                            character.uiDescription,
                            onBackClick = navController::popBackStack
                        )

                        is StoryTellerUiState.ErrorLoadingStory -> ErrorLoadingStoryScreen(
                            character.uiDescription,
                            onBackClick = navController::popBackStack,
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
                                onBackClick = {
                                    storyTellerViewModel.stopSpeaking()
                                    isPlaying = false
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}
