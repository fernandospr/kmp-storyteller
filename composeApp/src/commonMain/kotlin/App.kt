import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun App() {
    MaterialTheme {
        val storyTellerViewModel = getViewModel(Unit, viewModelFactory { StoryTellerViewModel() })
        val uiState by storyTellerViewModel.uiState.collectAsState()

        when (val state = uiState) {
            is StoryTellerUiState.Loading -> LoadingStoryScreen(state.uiDescription)
            is StoryTellerUiState.Story -> StoryScreen(state.story) { storyTellerViewModel.reset() }
            is StoryTellerUiState.Idle -> CharactersScreen { uiDescription, character ->
                storyTellerViewModel.newStory(
                    uiDescription,
                    character
                )
            }
        }
    }
}
