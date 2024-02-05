package com.github.fernandospr.storyteller

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale
import java.util.UUID

actual class TextToSpeech(context: Context) : TextToSpeech.OnInitListener {

    private val textToSpeechSystem = TextToSpeech(context.applicationContext, this)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeechSystem.setLanguage(Locale.getDefault())
        }
    }

    actual fun speak(text: String) {
        textToSpeechSystem.speak(
            text,
            TextToSpeech.QUEUE_ADD,
            null,
            UUID.randomUUID().toString()
        )
    }

    actual fun stopSpeaking() {
        textToSpeechSystem.stop()
    }
}