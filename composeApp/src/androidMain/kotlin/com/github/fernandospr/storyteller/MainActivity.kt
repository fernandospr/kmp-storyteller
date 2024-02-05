package com.github.fernandospr.storyteller

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import java.util.Locale
import java.util.UUID

class MainActivity : ComponentActivity() {

    private lateinit var textToSpeechSystem: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textToSpeechSystem = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechSystem.setLanguage(Locale.getDefault())
            }
        }

        fun speak(text: String) {
            textToSpeechSystem.speak(
                text,
                TextToSpeech.QUEUE_ADD,
                null,
                UUID.randomUUID().toString()
            )
        }

        setContent {
            App {
                speak(it)
            }
        }
    }
}