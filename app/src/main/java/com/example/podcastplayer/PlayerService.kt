package com.example.podcastplayer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class PlayerService : Service() {
    
    private val binder = PlayerBinder()
    private lateinit var player: ExoPlayer
    private var currentMediaItem: MediaItem? = null
    
    inner class PlayerBinder : Binder() {
        fun getService(): PlayerService = this@PlayerService
    }
    
    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this).build()
        
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
    
    fun play(mediaItem: MediaItem) {
        currentMediaItem = mediaItem
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
        updateNotification()
    }
    
    fun pause() {
        player.pause()
        updateNotification()
    }
    
    fun resume() {
        player.play()
        updateNotification()
    }
    
    fun stop() {
        player.stop()
    }
    
    fun getPlayer(): ExoPlayer {
        return player
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Podcast Player",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Podcast playback notifications"
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("播客播放器")
            .setContentText("正在播放播客...")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }
    
    private fun updateNotification() {
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, createNotification())
    }
    
    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
    
    companion object {
        private const val CHANNEL_ID = "podcast_player_channel"
        private const val NOTIFICATION_ID = 1
    }
}