package com.example.podcastplayer

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.podcastplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var player: ExoPlayer
    private var isPlaying = false
    
    // 示例播客列表
    private val podcastEpisodes = listOf(
        PodcastEpisode(1, "科技前沿", "第1期 - 区块链技术解析", "https://example.com/podcast1.mp3", "38:15"),
        PodcastEpisode(2, "科技前沿", "第2期 - 元宇宙与虚拟现实", "https://example.com/podcast2.mp3", "42:30"),
        PodcastEpisode(3, "科技前沿", "第3期 - 人工智能的未来", "https://example.com/podcast3.mp3", "45:20"),
        PodcastEpisode(4, "科技前沿", "第4期 - 量子计算入门", "https://example.com/podcast4.mp3", "35:45"),
        PodcastEpisode(5, "科技前沿", "第5期 - 自动驾驶技术进展", "https://example.com/podcast5.mp3", "41:10")
    )
    
    private var currentEpisodeIndex = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupPlayer()
        setupControls()
        setupSeekBar()
        loadEpisode(currentEpisodeIndex)
    }
    
    private fun setupPlayer() {
        player = ExoPlayer.Builder(this).build()
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_ENDED -> nextEpisode()
                    Player.STATE_READY -> updateDuration()
                }
            }
        })
        binding.playerView.player = player
    }
    
    private fun setupControls() {
        binding.playPauseBtn.setOnClickListener {
            if (isPlaying) {
                player.pause()
                binding.playPauseBtn.text = "▶️ 播放"
            } else {
                player.play()
                binding.playPauseBtn.text = "⏸️ 暂停"
            }
            isPlaying = !isPlaying
        }
        
        binding.prevBtn.setOnClickListener {
            previousEpisode()
        }
        
        binding.nextBtn.setOnClickListener {
            nextEpisode()
        }
    }
    
    private fun setupSeekBar() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && player.duration > 0) {
                    val position = (progress.toFloat() / 100 * player.duration).toLong()
                    player.seekTo(position)
                }
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    
    private fun loadEpisode(index: Int) {
        val episode = podcastEpisodes[index]
        binding.episodeTitle.text = episode.title
        binding.episodeSubtitle.text = episode.subtitle
        binding.durationText.text = episode.duration
        
        // 加载音频（这里使用示例URL）
        val mediaItem = MediaItem.fromUri(episode.audioUrl)
        player.setMediaItem(mediaItem)
        player.prepare()
    }
    
    private fun previousEpisode() {
        currentEpisodeIndex = (currentEpisodeIndex - 1 + podcastEpisodes.size) % podcastEpisodes.size
        loadEpisode(currentEpisodeIndex)
    }
    
    private fun nextEpisode() {
        currentEpisodeIndex = (currentEpisodeIndex + 1) % podcastEpisodes.size
        loadEpisode(currentEpisodeIndex)
    }
    
    private fun updateDuration() {
        val duration = player.duration
        if (duration > 0) {
            binding.seekBar.max = 100
            // 更新进度条
            binding.seekBar.progress = 0
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}

data class PodcastEpisode(
    val id: Int,
    val title: String,
    val subtitle: String,
    val audioUrl: String,
    val duration: String
)