# Kotlin Multiplatform Story Teller App

Choose a character 🐶🐱 and Gemini AI will generate a bedtime story for you.

### Showcase (sound on!)
https://github.com/fernandospr/kmp-storyteller/assets/4404680/7f6e3e9f-bbf5-48d5-b688-42383d5bb1e3

### Setting up your development environment
To setup the environment, please consult these [instructions](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-setup.html).

### Before running
* Get a [Gemini API Key](https://aistudio.google.com/app/apikey)
* Add to `local.properties` file located in the root directory (create if it doesn't exist):
`gemini.api_key=YOUR_KEY`

### Running the Android app
Open project in IntelliJ IDEA or Android Studio and run `composeApp` configuration.

### Running the iOS app
Open project in IntelliJ IDEA or Android Studio and run `iosApp` configuration.

Or open `iosApp/iosApp.xcodeproj` in Xcode and run it.

### Technologies
The app uses the following multiplatform dependencies in its implementation:
* [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) for UI
* [Ktor](https://ktor.io/) for networking with [Gemini API](https://ai.google.dev/docs/gemini_api_overview)
* [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) for JSON handling
* [moko-resources](https://github.com/icerockdev/moko-resources) for strings and font resources
* [moko-mvvm](https://github.com/icerockdev/moko-mvvm) to store and manage UI-related data
* [gradle-buildconfig-plugin](https://github.com/gmazzo/gradle-buildconfig-plugin) to expose the Gemini API Key as a constant from `local.properties`
* [PreCompose](https://github.com/Tlaster/PreCompose) for navigation between screens

To synthesize speech from the story text, it uses:
* [TextToSpeech](https://developer.android.com/reference/android/speech/tts/TextToSpeech) for Android
* [AVSpeechSynthesizer](https://developer.apple.com/documentation/avfaudio/avspeechsynthesizer) for iOS

### How to modify the AI prompt?
If you want the AI to generate other type of stories or make them longer, you can modify the prompt by opening the `strings.xml` files. You'll find one prompt string per language.

### How to add more characters?
Open `CharacterRepository` and modify `getAllCharacters()`: Add the character you desire by providing a **name**, **emoji** and a **background color**.

You have to declare the name in the `strings.xml` files. You'll find one name string per language.
