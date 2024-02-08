package com.github.fernandospr.storyteller

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.UUID

actual class TextToSpeech(context: Context) : TextToSpeech.OnInitListener {

    private val textToSpeechSystem = TextToSpeech(context.applicationContext, this)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeechSystem.setSpeechRate(0.6f)
        }
    }

    actual fun speak(text: String, onComplete: () -> Unit) {
        textToSpeechSystem.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {}

            override fun onDone(utteranceId: String?) { onComplete() }

            override fun onError(utteranceId: String?) {}
        })
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

    actual fun shutdown() {
        textToSpeechSystem.shutdown()
    }
}