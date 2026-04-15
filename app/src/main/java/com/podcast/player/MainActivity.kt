package com.podcast.player

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.podcast.player.databinding.ActivityMainBinding
import java.io.Serializable

class Episode(val title: String, val author: String, val duration: String, val audioUrl: String, val imageUrl: String): Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var episodes = mutableListOf<Episode>()
    private var adapter: EpisodeAdapter? = null
    private var currentEpisode: Episode? = null
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEpisodes()
        setupRecyclerView()
        setupControls()
    }

    private fun setupEpisodes() {
        episodes.add(Episode(
            "科技前沿：AI如何改变我们的生活",
            "科技博主小明",
            "15:30",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
            "https://picsum.photos/200/200?random=1"
        ))
        episodes.add(Episode(
            "读书分享：《人类简史》解读",
            "读书达人Lucy",
            "22:45",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
            "https://picsum.photos/200/200?random=2"
        ))
        episodes.add(Episode(
            "职场干货：面试技巧大公开",
            "HR张老师",
            "18:20",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3",
            "https://picsum.photos/200/200?random=3"
        ))
        episodes.add(Episode(
            "生活美学：家居收纳小妙招",
            "生活家小红",
            "12:15",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3",
            "https://picsum.photos/200/200?random=4"
        ))
        episodes.add(Episode(
            "英语学习：每日一句地道表达",
            "英语老师Jack",
            "08:00",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3",
            "https://picsum.photos/200/200?random=5"
        ))
    }

    private fun setupRecyclerView() {
        adapter = EpisodeAdapter(episodes) { episode ->
            playEpisode(episode)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun playEpisode(episode: Episode) {
        currentEpisode = episode
        binding.titleText.text = episode.title
        binding.authorText.text = episode.author
        isPlaying = true
        binding.playButton.text = "⏸"
        adapter?.notifyDataSetChanged()
    }

    private fun setupControls() {
        binding.playButton.setOnClickListener {
            if (currentEpisode == null && episodes.isNotEmpty()) {
                playEpisode(episodes[0])
            } else {
                isPlaying = !isPlaying
                binding.playButton.text = if (isPlaying) "⏸" else "▶"
            }
        }
    }
}