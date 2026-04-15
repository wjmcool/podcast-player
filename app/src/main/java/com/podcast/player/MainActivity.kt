package com.podcast.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.podcast.player.databinding.ActivityMainBinding
import com.podcast.player.data.Episode

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val episodes = mutableListOf<Episode>()
    private var adapter: EpisodeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initEpisodes()
        setupRecycler()
    }

    private fun initEpisodes() {
        episodes.add(Episode("Tech: AI changing our lives", "Tech Blogger Ming", "15:30", "url1", "img1"))
        episodes.add(Episode("Book Review: Sapiens", "Reader Lucy", "22:45", "url2", "img2"))
        episodes.add(Episode("Work: Interview Tips", "HR Teacher Zhang", "18:20", "url3", "img3"))
    }

    private fun setupRecycler() {
        adapter = EpisodeAdapter(episodes) { ep ->
            binding.titleText.text = ep.title
            binding.authorText.text = ep.author
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}