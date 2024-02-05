package com.github.fernandospr.storyteller

import platform.AVFAudio.AVSpeechBoundary
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance

actual class TextToSpeech {

    private val synthesizer = AVSpeechSynthesizer()
    private val voice = AVSpeechSynthesisVoice()
    
    actual fun speak(text: String) {
        val utterance = AVSpeechUtterance(string = text)
        utterance.voice = voice
        synthesizer.speakUtterance(utterance)
    }

    actual fun stopSpeaking() {
        synthesizer.stopSpeakingAtBoundary(AVSpeechBoundary.AVSpeechBoundaryImmediate)
    }
}