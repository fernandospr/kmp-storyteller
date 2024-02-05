package com.github.fernandospr.storyteller

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.Locale
import java.util.UUID

actual class TextToSpeech(context: Context) : TextToSpeech.OnInitListener {

    private val textToSpeechSystem = TextToSpeech(context.applicationContext, this)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeechSystem.setLanguage(Locale.getDefault())
        }
    }

    actual fun speak(text: String, onComplete: () -> Unit) {
        textToSpeechSystem.speak(
            text,
            TextToSpeech.QUEUE_ADD,
            null,
            UUID.randomUUID().toString()
        )
        textToSpeechSystem.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {}

            override fun onDone(utteranceId: String?) { onComplete() }

            override fun onError(utteranceId: String?) {}
        })
    }

    actual fun stopSpeaking() {
        textToSpeechSystem.stop()
    }
}