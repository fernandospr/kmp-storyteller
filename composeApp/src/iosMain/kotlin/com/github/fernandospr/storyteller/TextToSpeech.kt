package com.github.fernandospr.storyteller

import platform.AVFAudio.AVSpeechBoundary
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechSynthesizerDelegateProtocol
import platform.AVFAudio.AVSpeechUtterance
import platform.darwin.NSObject

actual class TextToSpeech {

    private val voice = AVSpeechSynthesisVoice()
    private val synthesizerDelegate = AVSpeechSynthesizerDelegate {}
    private val synthesizer = AVSpeechSynthesizer().apply { delegate = synthesizerDelegate }

    actual fun speak(text: String, onComplete: () -> Unit) {
        val utterance = AVSpeechUtterance(string = text)
        utterance.voice = voice
        synthesizer.speakUtterance(utterance)
        synthesizerDelegate.onComplete = onComplete
    }

    actual fun stopSpeaking() {
        synthesizer.stopSpeakingAtBoundary(AVSpeechBoundary.AVSpeechBoundaryImmediate)
    }
}

class AVSpeechSynthesizerDelegate(
    var onComplete: () -> Unit
) : NSObject(), AVSpeechSynthesizerDelegateProtocol {
    override fun speechSynthesizer(
        synthesizer: AVSpeechSynthesizer,
        didFinishSpeechUtterance: AVSpeechUtterance
    ) {
        onComplete()
    }
}
