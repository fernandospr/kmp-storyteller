package com.github.fernandospr.storyteller

expect class TextToSpeech {
    fun speak(text: String, onComplete: () -> Unit)
    fun stopSpeaking()
}