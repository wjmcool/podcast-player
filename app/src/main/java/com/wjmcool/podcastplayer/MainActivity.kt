package com.wjmcool.podcastplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView

    private val programs = listOf(
        Program("AI未来", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", "探讨人工智能的最新发展"),
        Program("科技热点", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3", "每周科技新闻汇总"),
        Program("生活小贴士", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3", "实用生活技巧")
    )

    data class Program(val title: String, val url: String, val description: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerView = findViewById(R.id.player_view)
        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        val programContainer = findViewById<android.widget.LinearLayout>(R.id.program_container)

        programs.forEach { program ->
            val card = layoutInflater.inflate(R.layout.program_item, programContainer, false) as MaterialCardView
            card.findViewById<android.widget.TextView>(R.id.title).text = program.title
            card.findViewById<android.widget.TextView>(R.id.description).text = program.description
            card.setOnClickListener {
                playProgram(program.url)
            }
            programContainer.addView(card)
        }

        findViewById<MaterialButton>(R.id.play_btn).setOnClickListener { player.play() }
        findViewById<MaterialButton>(R.id.pause_btn).setOnClickListener { player.pause() }
    }

    private fun playProgram(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}