package com.xiaobao.podcastplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.IOException

data class PodcastEpisode(
    val id: Int,
    val title: String,
    val author: String,
    val duration: String,
    val audioUrl: String
)

class PodcastAdapter(
    private val episodes: List<PodcastEpisode>,
    private val onEpisodeClick: (PodcastEpisode) -> Unit
) : RecyclerView.Adapter<PodcastAdapter.EpisodeViewHolder>() {

    var playingPosition: Int = -1

    class EpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.episodeTitle)
        val author: TextView = view.findViewById(R.id.episodeAuthor)
        val duration: TextView = view.findViewById(R.id.episodeDuration)
        val playIcon: ImageView = view.findViewById(R.id.playIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_episode, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = episodes[position]
        holder.title.text = episode.title
        holder.author.text = episode.author
        holder.duration.text = episode.duration
        
        if (position == playingPosition) {
            holder.playIcon.setImageResource(android.R.drawable.ic_media_pause)
        } else {
            holder.playIcon.setImageResource(android.R.drawable.ic_media_play)
        }

        holder.itemView.setOnClickListener {
            val previousPosition: Int = playingPosition
            playingPosition = position
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition)
            }
            notifyItemChanged(position)
            onEpisodeClick.invoke(episode)
        }
    }

    override fun getItemCount(): Int = episodes.size
}

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var episodeTitle: TextView
    private lateinit var episodeAuthor: TextView
    private lateinit var btnPlayPause: FloatingActionButton
    private lateinit var btnPrev: ImageView
    private lateinit var btnNext: ImageView
    private lateinit var progressText: TextView

    private var mediaPlayer: MediaPlayer? = null
    private var currentEpisode: PodcastEpisode? = null
    private var isPlaying: Boolean = false
    private val handler: Handler = Handler(Looper.getMainLooper())
    private var currentEpisodeIndex: Int = 0

    private val episodes: List<PodcastEpisode> = listOf(
        PodcastEpisode(
            1,
            "欢迎收听小宝播客",
            "小宝团队",
            "3:00",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
        ),
        PodcastEpisode(
            2,
            "科技前沿：AI的未来",
            "科技观察家",
            "4:00",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"
        ),
        PodcastEpisode(
            3,
            "音乐鉴赏：古典之美",
            "音乐达人",
            "5:00",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
        ),
        PodcastEpisode(
            4,
            "生活窍门：健康饮食",
            "Lifestyle专家",
            "3:30",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3"
        ),
        PodcastEpisode(
            5,
            "读书分享：深度阅读",
            "书友会",
            "4:30",
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3"
        )
    )

    private val updateProgressRunnable: Runnable = object : Runnable {
        override fun run() {
            val player: MediaPlayer? = mediaPlayer
            if (player != null && player.isPlaying) {
                val currentPosition: Int = player.currentPosition / 1000
                val minutes: Int = currentPosition / 60
                val seconds: Int = currentPosition % 60
                progressText.text = String.format("%02d:%02d", minutes, seconds)
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        episodeTitle = findViewById(R.id.currentEpisodeTitle)
        episodeAuthor = findViewById(R.id.currentEpisodeAuthor)
        btnPlayPause = findViewById(R.id.btnPlayPause)
        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)
        progressText = findViewById(R.id.progressText)

        setupRecyclerView()
        setupControls()

        if (episodes.isNotEmpty()) {
            loadEpisode(episodes[0], 0)
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PodcastAdapter(episodes) { episode ->
            val index: Int = episodes.indexOf(episode)
            loadEpisode(episode, index)
            playEpisode()
        }
    }

    private fun setupControls() {
        btnPlayPause.setOnClickListener {
            if (isPlaying) {
                pauseEpisode()
            } else {
                playEpisode()
            }
        }

        btnPrev.setOnClickListener {
            if (currentEpisodeIndex > 0) {
                currentEpisodeIndex--
                loadEpisode(episodes[currentEpisodeIndex], currentEpisodeIndex)
                playEpisode()
            }
        }

        btnNext.setOnClickListener {
            if (currentEpisodeIndex < episodes.size - 1) {
                currentEpisodeIndex++
                loadEpisode(episodes[currentEpisodeIndex], currentEpisodeIndex)
                playEpisode()
            }
        }
    }

    private fun loadEpisode(episode: PodcastEpisode, index: Int) {
        currentEpisode = episode
        currentEpisodeIndex = index
        episodeTitle.text = episode.title
        episodeAuthor.text = episode.author
        progressText.text = "00:00"
        
        stopMediaPlayer()
        isPlaying = false
        updatePlayPauseButton()
    }

    private fun playEpisode() {
        val episode: PodcastEpisode? = currentEpisode
        if (episode != null) {
            try {
                val player: MediaPlayer = MediaPlayer()
                mediaPlayer = player
                player.setDataSource(episode.audioUrl)
                player.setOnPreparedListener {
                    it.start()
                    isPlaying = true
                    updatePlayPauseButton()
                    handler.post(updateProgressRunnable)
                }
                player.setOnCompletionListener {
                    isPlaying = false
                    updatePlayPauseButton()
                    handler.removeCallbacks(updateProgressRunnable)
                    if (currentEpisodeIndex < episodes.size - 1) {
                        currentEpisodeIndex++
                        loadEpisode(episodes[currentEpisodeIndex], currentEpisodeIndex)
                        playEpisode()
                    }
                }
                player.setOnErrorListener { _, what, extra ->
                    Toast.makeText(
                        this@MainActivity,
                        "播放错误: $what, $extra",
                        Toast.LENGTH_SHORT
                    ).show()
                    isPlaying = false
                    updatePlayPauseButton()
                    true
                }
                player.prepareAsync()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "无法加载音频", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun pauseEpisode() {
        val player: MediaPlayer? = mediaPlayer
        if (player != null) {
            player.pause()
        }
        isPlaying = false
        updatePlayPauseButton()
        handler.removeCallbacks(updateProgressRunnable)
    }

    private fun stopMediaPlayer() {
        val player: MediaPlayer? = mediaPlayer
        if (player != null) {
            player.stop()
            player.release()
        }
        mediaPlayer = null
        handler.removeCallbacks(updateProgressRunnable)
    }

    private fun updatePlayPauseButton() {
        if (isPlaying) {
            btnPlayPause.setImageResource(android.R.drawable.ic_media_pause)
        } else {
            btnPlayPause.setImageResource(android.R.drawable.ic_media_play)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMediaPlayer()
    }
}
