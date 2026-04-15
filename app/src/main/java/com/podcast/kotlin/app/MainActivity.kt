package com.podcast.kotlin.app

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.podcast.kotlin.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    data class Podcast(val id: Int, val title: String, val duration: Int)

    private val podcasts = listOf(
        Podcast(1, "科技前沿", 1800),
        Podcast(2, "商业洞察", 2100),
        Podcast(3, "生活哲学", 2400),
        Podcast(4, "历史故事", 1900)
    )

    private var currentPodcast: Podcast? = null
    private var isPlaying = false
    private var currentTime = 0.0
    private var volume = 0.7

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var playbackRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPodcastList()
        setupControls()
        setupVolumeControl()

        // Select first podcast by default
        selectPodcast(podcasts[0])
    }

    private fun setupPodcastList() {
        val listContainer = binding.podcastList
        podcasts.forEach { podcast ->
            val itemView = LayoutInflater.from(this).inflate(R.layout.podcast_item, listContainer, false)
            itemView.findViewById<TextView>(R.id.podcastTitle).text = podcast.title
            itemView.findViewById<TextView>(R.id.podcastDesc).text = when(podcast.id) {
                1 -> "探索最新的科技趋势和创新"
                2 -> "商业策略与市场分析"
                3 -> "思考人生的意义与价值"
                4 -> "讲述历史上的精彩故事"
                else -> ""
            }
            itemView.setOnClickListener {
                selectPodcast(podcast)
                updatePodcastSelection(podcast.id)
            }
            listContainer.addView(itemView)
        }
    }

    private fun setupControls() {
        binding.playBtn.setOnClickListener {
            if (currentPodcast != null) {
                isPlaying = !isPlaying
                updatePlayButton()
                if (isPlaying) {
                    startPlayback()
                } else {
                    stopPlayback()
                }
            } else {
                binding.statusText.text = "⚠️ 请先选择一个播客"
            }
        }

        binding.prevBtn.setOnClickListener {
            if (currentPodcast != null) {
                val index = podcasts.indexOf(currentPodcast)
                val prevIndex = (index - 1 + podcasts.size) % podcasts.size
                selectPodcast(podcasts[prevIndex])
                updatePodcastSelection(podcasts[prevIndex].id)
            }
        }

        binding.nextBtn.setOnClickListener {
            if (currentPodcast != null) {
                val index = podcasts.indexOf(currentPodcast)
                val nextIndex = (index + 1) % podcasts.size
                selectPodcast(podcasts[nextIndex])
                updatePodcastSelection(podcasts[nextIndex].id)
            }
        }

        binding.rewindBtn.setOnClickListener {
            if (currentPodcast != null) {
                currentTime = Math.max(0.0, currentTime - 15.0)
                updateTimeDisplay()
                updateProgress()
            }
        }

        binding.forwardBtn.setOnClickListener {
            if (currentPodcast != null) {
                currentTime = Math.min(currentPodcast!!.duration.toDouble(), currentTime + 15.0)
                updateTimeDisplay()
                updateProgress()
            }
        }

        binding.progressBar.setOnClickListener { view ->
            if (currentPodcast != null) {
                val percent = view.width.toFloat() / view.width.toFloat()
                currentTime = percent * currentPodcast!!.duration
                updateTimeDisplay()
                updateProgress()
            }
        }
    }

    private fun setupVolumeControl() {
        binding.volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                volume = progress / 100.0
                binding.volumeValueText.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun selectPodcast(podcast: Podcast) {
        currentPodcast = podcast
        currentTime = 0.0
        isPlaying = false
        updatePlayButton()

        binding.currentPodcastText.text = podcast.title
        binding.totalTimeText.text = formatTime(podcast.duration)
        binding.statusText.text = "📡 已选择: ${podcast.title} - 点击播放"
        updateProgress()
        updateTimeDisplay()
    }

    private fun updatePodcastSelection(selectedId: Int) {
        val listContainer = binding.podcastList
        for (i in 0 until listContainer.childCount) {
            val itemView = listContainer.getChildAt(i)
            val titleView = itemView.findViewById<TextView>(R.id.podcastTitle)
            val isSelected = podcasts[i].id == selectedId
            itemView.isSelected = isSelected
            if (isSelected) {
                itemView.setBackgroundColor(getColor(R.color.teal_200))
                titleView.setTextColor(getColor(R.color.black))
            } else {
                itemView.setBackgroundColor(0)
                titleView.setTextColor(getColor(R.color.white))
            }
        }
    }

    private fun updatePlayButton() {
        binding.playBtn.setImageResource(
            if (isPlaying) android.R.drawable.ic_media_pause
            else android.R.drawable.ic_media_play
        )
        binding.statusText.text = if (isPlaying) {
            "▶️ 正在播放: ${currentPodcast?.title}"
        } else {
            "⏸ 已暂停: ${currentPodcast?.title}"
        }
    }

    private fun startPlayback() {
        playbackRunnable = object : Runnable {
            override fun run() {
                if (isPlaying && currentPodcast != null) {
                    currentTime += 0.1
                    if (currentTime >= currentPodcast!!.duration) {
                        currentTime = 0.0
                        isPlaying = false
                        updatePlayButton()
                        binding.statusText.text = "✅ 播放完成"
                    } else {
                        updateTimeDisplay()
                        updateProgress()
                        handler.postDelayed(this, 100)
                    }
                }
            }
        }
        handler.post(playbackRunnable)
    }

    private fun stopPlayback() {
        handler.removeCallbacks(playbackRunnable)
    }

    private fun updateTimeDisplay() {
        binding.currentTimeText.text = formatTime(currentTime.toInt())
    }

    private fun updateProgress() {
        if (currentPodcast != null) {
            val progress = (currentTime / currentPodcast!!.duration * 100).toInt()
            binding.progressBar.progress = progress
        }
    }

    private fun formatTime(seconds: Int): String {
        val mins = seconds / 60
        val secs = seconds % 60
        return String.format("%d:%02d", mins, secs)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopPlayback()
    }
}