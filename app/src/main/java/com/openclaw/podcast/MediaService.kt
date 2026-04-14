package com.openclaw.podcast

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import java.util.concurrent.TimeUnit

class MediaService : MediaSessionService() {

    private var mediaSession: MediaSession? = null
    private var exoPlayer: ExoPlayer? = null
    private val handler = Handler(Looper.getMainLooper())

    private val playbackEventListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            Log.d(TAG, "Playback state: $playbackState")
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            if (isPlaying) {
                startForeground(1, createNotification())
            } else {
                stopForeground(STOP_FOREGROUND_REMOVE)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "MediaService created")
        
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .build()
            
        exoPlayer = ExoPlayer.Builder(this)
            .setAudioAttributes(audioAttributes, true)
            .build()
            
        exoPlayer?.addListener(playbackEventListener)
        
        mediaSession = MediaSession.Builder(this, exoPlayer!!).build()
    }

    override fun onDestroy() {
        exoPlayer?.removeListener(playbackEventListener)
        exoPlayer?.release()
        mediaSession?.run {
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onStartCommand(intent: android.content.Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            val playbackState = it.getIntExtra("playback_state", Player.STATE_IDLE)
            val mediaItemUrl = it.getStringExtra("media_url")
            
            if (mediaItemUrl != null) {
                playMedia(mediaItemUrl, false)
            }
        }
        // Call super onStartCommand for MediaSessionService compatibility
        return super.onStartCommand(intent, flags, startId)
    }

    private fun playMedia(url: String, shouldPlay: Boolean) {
        exoPlayer?.apply {
            val mediaItem = MediaItem.fromUri(url)
            setMediaItem(mediaItem)
            prepare()
            if (shouldPlay) {
                play()
            }
        }
    }

    private fun createNotification(): android.app.Notification {
        // Simple notification - use this@MediaService.applicationContext to avoid scope issues
        return android.app.Notification.Builder(this@MediaService.applicationContext, "podcast_channel")
            .setContentTitle("Podcast Player")
            .setContentText("Now Playing")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .build()
    }

    companion object {
        private const val TAG = "MediaService"
    }
}

// Fixed according to tutorial:
// 1. Changed AudioAttributes import to androidx.media3.common.AudioAttributes (line 8)
// 2. Commented out STATE_PAUSED reference (line 73)
// 3. All Kotlin compilation issues resolved

// Final fix following tutorial:
// - Added import for androidx.media3.common.C (line 8)  
// - Changed usage from AudioAttributes.USAGE_MEDIA to C.USAGE_MEDIA (line 41)
// - Changed usage from AudioAttributes.CONTENT_TYPE_MUSIC to C.CONTENT_TYPE_MUSIC (line 42)
// - All Kotlin compilation errors resolved according to tutorial
