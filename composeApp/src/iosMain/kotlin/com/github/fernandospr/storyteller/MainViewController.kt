package com.github.fernandospr.storyteller

import androidx.compose.ui.window.ComposeUIViewController
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance

fun MainViewController() = ComposeUIViewController {
    App {
        speak(it)
    }
}

private fun speak(text: String) {
    val utterance = AVSpeechUtterance(string = text)
    val voice = AVSpeechSynthesisVoice()
    utterance.voice = voice
    val synthesizer = AVSpeechSynthesizer()
    synthesizer.speakUtterance(utterance)
}