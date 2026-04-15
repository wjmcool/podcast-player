package com.podcast.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.podcast.player.databinding.ActivityMainBinding

class Episode(var title: String, var author: String, var duration: String, var audioUrl: String, var imageUrl: String)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val episodes = mutableListOf<Episode>()
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
            "Tech: AI changing our lives",
            "Tech Blogger Ming",
            "15:30",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
            "https://picsum.photos/200/200?random=1"
        ))
        episodes.add(Episode(
            "Book Review: Sapiens",
            "Reader Lucy",
            "22:45",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
            "https://picsum.photos/200/200?random=2"
        ))
        episodes.add(Episode(
            "Work: Interview Tips",
            "HR Teacher Zhang",
            "18:20",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3",
            "https://picsum.photos/200/200?random=3"
        ))
        episodes.add(Episode(
            "Life: Home Organization",
            "Lifestyle Expert Red",
            "12:15",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3",
            "https://picsum.photos/200/200?random=4"
        ))
        episodes.add(Episode(
            "English: Daily Expression",
            "Teacher Jack",
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
        binding.playButton.text = "||"
        adapter?.notifyDataSetChanged()
    }

    private fun setupControls() {
        binding.playButton.setOnClickListener {
            if (currentEpisode == null && episodes.isNotEmpty()) {
                playEpisode(episodes[0])
            } else {
                isPlaying = !isPlaying
                binding.playButton.text = if (isPlaying) "||" else ">"
            }
        }
    }
}