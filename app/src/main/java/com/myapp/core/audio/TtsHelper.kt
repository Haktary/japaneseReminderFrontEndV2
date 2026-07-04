package com.myapp.core.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TtsHelper @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var pendingSpeak: ((TextToSpeech) -> Unit)? = null

    fun init() {
        if (isInitialized) return
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isInitialized = true
                tts?.language = Locale.JAPANESE
                pendingSpeak?.invoke(tts!!)
                pendingSpeak = null
            }
        }
    }

    fun speak(text: String, onDone: (() -> Unit)? = null) {
        val engine = tts
        if (engine != null && isInitialized) {
            engine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) {
                    onDone?.invoke()
                }
                override fun onError(utteranceId: String?) {}
            })
            engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts_utterance")
        } else {
            pendingSpeak = { engine ->
                engine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {}
                    override fun onDone(utteranceId: String?) {
                        onDone?.invoke()
                    }
                    override fun onError(utteranceId: String?) {}
                })
                engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts_utterance")
            }
            init()
        }
    }

    fun stop() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        isInitialized = false
    }
}
