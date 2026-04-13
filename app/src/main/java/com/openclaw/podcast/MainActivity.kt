package com.openclaw.podcast

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclaw.podcast.adapter.PodcastAdapter
import com.openclaw.podcast.databinding.ActivityMainBinding
import com.openclaw.podcast.model.Podcast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var exoPlayer: ExoPlayer? = null
    private var isPlaying = false

    private val podcastList = listOf(
        Podcast(
            id = 1,
            title = "科技前沿",
            description = "探索最新科技趋势和创新技术",
            icon = "🔬",
            imageUrl = "",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
            duration = 1800
        ),
        Podcast(
            id = 2,
            title = "历史故事",
            description = "探索历史上的有趣故事",
            icon = "📚",
            imageUrl = "",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
            duration = 2400
        ),
        Podcast(
            id = 3,
            title = "健康养生",
            description = "关注身心健康与生活方式",
            icon = "🌱",
            imageUrl = "",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3",
            duration = 1500
        ),
        Podcast(
            id = 4,
            title = "商业思维",
            description = "商业洞察与创业经验分享",
            icon = "💼",
            imageUrl = "",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3",
            duration = 2100
        ),
        Podcast(
            id = 5,
            title = "艺术欣赏",
            description = "音乐、绘画与文学赏析",
            icon = "🎨",
            imageUrl = "",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3",
            duration = 1920
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "播客 APP"

        setupRecyclerView()
        setupExoPlayer()
        setupPlayerControls()
    }

    override fun onDestroy() {
        exoPlayer?.release()
        super.onDestroy()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PodcastAdapter(podcastList) { podcast ->
                playPodcast(podcast)
            }
        }
    }

    private fun setupExoPlayer() {
        exoPlayer = ExoPlayer.Builder(this)
            .build()
            .apply {
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        Log.d("MainActivity", "Playback state: $playbackState")
                        when (playbackState) {
                            Player.STATE_READY -> {
                                binding.playpauseBtn.setImageResource(android.R.drawable.ic_media_play)
                                isPlaying = true
                            }
                            Player.STATE_BUFFERING -> {
                                binding.playpauseBtn.setImageResource(android.R.drawable.ic_media_pause)
                            }
                            else -> {}
                        }
                    }
                })
            }
    }

    private fun setupPlayerControls() {
        binding.playpauseBtn.setOnClickListener {
            exoPlayer?.let { player ->
                if (player.isPlaying) {
                    player.pause()
                    binding.playpauseBtn.setImageResource(android.R.drawable.ic_media_play)
                } else {
                    player.play()
                    binding.playpauseBtn.setImageResource(android.R.drawable.ic_media_pause)
                }
            }
        }

        binding.skipbackwardBtn.setOnClickListener {
            exoPlayer?.seekTo(exoPlayer?.currentPosition?.minus(10000) ?: 0)
        }

        binding.skipforwardBtn.setOnClickListener {
            exoPlayer?.seekTo(exoPlayer?.currentPosition?.plus(10000) ?: 0)
        }
    }

    private fun playPodcast(podcast: Podcast) {
        binding.currentPodcastTitle.text = podcast.title
        binding.currentPodcastDesc.text = podcast.description

        exoPlayer?.let { player ->
            val mediaItem = MediaItem.fromUri(podcast.audioUrl)
            player.setMediaItem(mediaItem)
            player.prepare()
            
            player.playWhenReady = true
            binding.playpauseBtn.setImageResource(android.R.drawable.ic_media_pause)
        }
    }
}
