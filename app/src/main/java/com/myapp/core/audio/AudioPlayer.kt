package com.myapp.core.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioPlayer @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private var mediaPlayer: MediaPlayer? = null

    fun playFromUrl(url: String, onCompletion: (() -> Unit)? = null) {
        release()
        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(url)
                setOnPreparedListener { start() }
                setOnCompletionListener { onCompletion?.invoke() }
                setOnErrorListener { _, _, _ -> onCompletion?.invoke(); true }
                prepareAsync()
            }
        } catch (e: Exception) {
            onCompletion?.invoke()
        }
    }

    fun playFromFile(file: File, onCompletion: (() -> Unit)? = null) {
        release()
        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(file.absolutePath)
                setOnPreparedListener { start() }
                setOnCompletionListener { onCompletion?.invoke() }
                setOnErrorListener { _, _, _ -> onCompletion?.invoke(); true }
                prepareAsync()
            }
        } catch (e: Exception) {
            onCompletion?.invoke()
        }
    }

    fun playFromUri(uri: Uri, onCompletion: (() -> Unit)? = null) {
        release()
        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(context, uri)
                setOnPreparedListener { start() }
                setOnCompletionListener { onCompletion?.invoke() }
                setOnErrorListener { _, _, _ -> onCompletion?.invoke(); true }
                prepareAsync()
            }
        } catch (e: Exception) {
            onCompletion?.invoke()
        }
    }

    fun stop() {
        mediaPlayer?.let {
            try {
                it.stop()
                it.release()
            } catch (_: Exception) {
            }
        }
        mediaPlayer = null
    }

    fun pause() {
        mediaPlayer?.let {
            if (it.isPlaying) it.pause()
        }
    }

    fun resume() {
        mediaPlayer?.let {
            if (!it.isPlaying) it.start()
        }
    }

    val isPlaying: Boolean
        get() = mediaPlayer?.isPlaying ?: false

    private fun release() {
        stop()
    }
}
