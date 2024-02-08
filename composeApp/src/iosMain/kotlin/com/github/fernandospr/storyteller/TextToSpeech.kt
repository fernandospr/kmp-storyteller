package com.github.fernandospr.storyteller

import platform.AVFAudio.AVSpeechBoundary
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechSynthesizerDelegateProtocol
import platform.AVFAudio.AVSpeechUtterance
import platform.darwin.NSObject

actual class TextToSpeech {

    private val synthesisVoice = AVSpeechSynthesisVoice()
    private val synthesizerDelegate = AVSpeechSynthesizerDelegate {}
    private val synthesizer = AVSpeechSynthesizer().apply { delegate = synthesizerDelegate }

    actual fun speak(text: String, onComplete: () -> Unit) {
        val utterance = AVSpeechUtterance(string = text).apply {
            rate = 0.35f
            voice = synthesisVoice
        }
        synthesizerDelegate.onComplete = onComplete
        synthesizer.speakUtterance(utterance)
    }

    actual fun stopSpeaking() {
        synthesizer.stopSpeakingAtBoundary(AVSpeechBoundary.AVSpeechBoundaryImmediate)
    }

    actual fun shutdown() {
        // no-op
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
