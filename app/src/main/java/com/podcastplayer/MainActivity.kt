package com.podcastplayer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: EpisodesAdapter
    private var player: ExoPlayer? = null
    private var isPlaying = false
    private var currentIndex = 0
    private val handler = Handler(Looper.getMainLooper())
    private var updateRunnable: Runnable? = null

    private val episodes = listOf(
        Episode(1, "第1集：AI助手的诞生", "探讨智能助手的发展历程", "45:30", 2730000,
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", "2026-04-14"),
        Episode(2, "第2集：播客制作入门", "从零开始学习播客录制与剪辑", "38:15", 2295000,
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3", "2026-04-13"),
        Episode(3, "第3集：OpenClaw使用技巧", "深度解析OpenClaw功能", "52:00", 3120000,
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3", "2026-04-12"),
        Episode(4, "第4集：Android开发实战", "从零开发Android应用", "41:45", 2505000,
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3", "2026-04-11"),
        Episode(5, "第5集：Web3.0的未来", "去中心化网络探讨", "35:20", 2120000,
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3", "2026-04-10")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout programmatically (no custom layout files needed)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = EpisodesAdapter(episodes) { episode ->
            currentIndex = episodes.indexOf(episode)
            playEpisode(episode)
        }
        binding.rvEpisodes.layoutManager = LinearLayoutManager(this)
        binding.rvEpisodes.adapter = adapter

        player = ExoPlayer.Builder(this).build()
        player?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) playNext()
            }
        })

        binding.btnPlayPause.setOnClickListener { togglePlay() }
        binding.btnPrevious.setOnClickListener { playPrevious() }
        binding.btnNext.setOnClickListener { playNext() }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && player?.duration != null) {
                    player?.seekTo((progress / 1000f * player!!.duration).toLong())
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun playEpisode(episode: Episode) {
        player?.stop()
        player?.setMediaItem(MediaItem.fromUri(episode.audioUrl))
        player?.prepare()
        player?.play()
        isPlaying = true
        updateUI()
        startUpdates()
    }

    private fun togglePlay() {
        if (isPlaying) pause() else playCurrent()
    }

    private fun playCurrent() {
        episodes.getOrNull(currentIndex)?.let { playEpisode(it) }
    }

    private fun pause() {
        player?.pause()
        isPlaying = false
        updateUI()
        stopUpdates()
    }

    private fun playNext() {
        if (currentIndex < episodes.size - 1) {
            currentIndex++
            playEpisode(episodes[currentIndex])
        }
    }

    private fun playPrevious() {
        if (currentIndex > 0) {
            currentIndex--
            playEpisode(episodes[currentIndex])
        }
    }

    private fun updateUI() {
        val ep = episodes[currentIndex]
        binding.tvCurrentTitle.text = ep.title
        binding.btnPlayPause.setImageResource(
            if (isPlaying) android.R.drawable.ic_media_pause
            else android.R.drawable.ic_media_play
        )
    }

    private fun startUpdates() {
        updateRunnable?.let { handler.removeCallbacks(it) }
        updateRunnable = object : Runnable {
            override fun run() {
                player?.let { p ->
                    if (p.duration > 0) {
                        val progress = (p.currentPosition * 1000) / p.duration
                        binding.seekBar.progress = progress.toInt()
                        binding.tvCurrentTime.text = formatTime(p.currentPosition)
                        binding.tvTotalTime.text = formatTime(p.duration)
                    }
                    if (isPlaying) handler.postDelayed(this, 1000)
                }
            }
        }
        handler.post(updateRunnable as Runnable)
    }

    private fun stopUpdates() {
        updateRunnable?.let { handler.removeCallbacks(it) }
    }

    private fun formatTime(ms: Long): String {
        val min = TimeUnit.MILLISECONDS.toMinutes(ms)
        val sec = TimeUnit.MILLISECONDS.toSeconds(ms) % 60
        return String.format("%d:%02d", min, sec)
    }

    override fun onDestroy() {
        player?.release()
        player = null
        stopUpdates()
        super.onDestroy()
    }
}
