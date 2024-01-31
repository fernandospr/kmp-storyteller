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
        val characters = getCharacters()

        when (val state = uiState) {
            is StoryTellerUiState.LoadingStory -> LoadingStoryScreen(state.uiDescription)

            is StoryTellerUiState.Story -> StoryScreen(
                state.uiDescription,
                state.story
            ) { storyTellerViewModel.reset() }

            is StoryTellerUiState.CharacterSelection -> CharacterSelectionScreen(
                characters
            ) { character ->
                storyTellerViewModel.newStory(character)
            }
        }
    }
}

private fun getCharacters() = listOf(
    Character("dog", "🐶"),
    Character("cat", "🐱"),
    Character("monkey", "🐵"),
    Character("lion", "🦁"),
    Character("duck", "🦆"),
    Character("rabbit", "🐰"),
    Character("cow", "🐮"),
    Character("pig", "🐷"),
    Character("horse", "🐴"),
    Character("frog", "🐸"),
)
