import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        val storyTellerViewModel = getViewModel(Unit, viewModelFactory { StoryTellerViewModel() })
        val uiState by storyTellerViewModel.uiState.collectAsState()
        LaunchedEffect(storyTellerViewModel) {
            storyTellerViewModel.newStory("cat")
        }

        Text(uiState.story, modifier = Modifier.verticalScroll(rememberScrollState()))
    }
}