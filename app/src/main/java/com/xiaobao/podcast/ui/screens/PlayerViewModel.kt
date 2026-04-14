package com.xiaobao.podcast.ui.screens

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.xiaobao.podcast.data.model.Episode
import com.xiaobao.podcast.data.repository.SampleEpisodes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {

    val episodes: List<Episode> = SampleEpisodes.episodes

    private val _currentEpisode = MutableStateFlow<Episode?>(null)
    val currentEpisode: StateFlow<Episode?> = _currentEpisode.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val _playbackState = MutableStateFlow("")
    val playbackState: StateFlow<String> = _playbackState.asStateFlow()

    private var exoPlayer: ExoPlayer? = null
    private var currentIndex = -1

    fun playEpisode(index: Int) {
        if (index < 0 || index >= episodes.size) return
        currentIndex = index
        val ep = episodes[index]
        _currentEpisode.value = ep

        exoPlayer?.release()

        exoPlayer = ExoPlayer.Builder(android.app.Application()).build().apply {
            val mediaItem = MediaItem.fromUri(ep.audioUrl)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    when (state) {
                        Player.STATE_READY -> _playbackState.value = "播放中"
                        Player.STATE_ENDED -> playNext()
                        Player.STATE_BUFFERING -> _playbackState.value = "缓冲中..."
                        Player.STATE_IDLE -> _playbackState.value = "待机"
                    }
                }
                override fun onIsPlayingChanged(playing: Boolean) {
                    _isPlaying.value = playing
                }
            })
        }

        startProgressTracking()
    }

    fun togglePlayPause() {
        exoPlayer?.let {
            if (it.isPlaying) it.pause() else it.play()
        }
    }

    fun playNext() {
        if (currentIndex < episodes.size - 1) {
            playEpisode(currentIndex + 1)
        }
    }

    fun playPrev() {
        if (currentIndex > 0) {
            playEpisode(currentIndex - 1)
        }
    }

    private fun startProgressTracking() {
        viewModelScope.launch {
            while (isActive) {
                delay(500)
                exoPlayer?.let { player ->
                    if (player.duration > 0) {
                        _progress.value = player.currentPosition.toFloat() / player.duration.toFloat()
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
    }
}
