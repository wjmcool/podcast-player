package com.example.podcastplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var playBtn: ImageButton
    private lateinit var prevBtn: ImageButton
    private lateinit var nextBtn: ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var currentTimeText: TextView
    private lateinit var durationText: TextView
    private lateinit var volumeSlider: SeekBar
    private lateinit var statusText: TextView
    private lateinit var podcastSpinner: Spinner

    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var currentPosition = 0
    private var duration = 0
    private val handler = Handler(Looper.getMainLooper())
    private val progressUpdateRunnable = Runnable { updateProgress() }

    private val podcastItems = arrayOf(
        "科技前沿 - 最新科技资讯",
        "每日新闻 - 国内国际要闻",
        "有声读物 - 经典小说推荐",
        "音乐推荐 - 高品质音乐精选",
        "财经观察 - 投资理财分析"
    )

    // 替换为实际的音频资源或URL
    private val podcastUrls = arrayOf(
        "",
        "",
        "",
        "",
        ""
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playBtn = findViewById(R.id.playBtn)
        prevBtn = findViewById(R.id.prevBtn)
        nextBtn = findViewById(R.id.nextBtn)
        progressBar = findViewById(R.id.progressBar)
        currentTimeText = findViewById(R.id.currentTimeText)
        durationText = findViewById(R.id.durationText)
        volumeSlider = findViewById(R.id.volumeSlider)
        statusText = findViewById(R.id.statusText)
        podcastSpinner = findViewById(R.id.podcastSpinner)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, podcastItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        podcastSpinner.adapter = adapter

        podcastSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                loadPodcast(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        playBtn.setOnClickListener {
            togglePlayPause()
        }

        prevBtn.setOnClickListener {
            val currentPosition = podcastSpinner.selectedItemPosition
            if (currentPosition > 0) {
                podcastSpinner.setSelection(currentPosition - 1)
            }
        }

        nextBtn.setOnClickListener {
            val currentPosition = podcastSpinner.selectedItemPosition
            if (currentPosition < podcastItems.size - 1) {
                podcastSpinner.setSelection(currentPosition + 1)
            }
        }

        progressBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && isPlaying) {
                    currentPosition = progress
                    mediaPlayer?.seekTo(currentPosition)
                    currentTimeText.text = formatTime(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        volumeSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.setVolume((progress / 100f), (progress / 100f))
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun loadPodcast(position: Int) {
        // 释放之前的媒体播放器
        mediaPlayer?.release()
        mediaPlayer = null

        // 初始化新的媒体播放器
        try {
            if (podcastUrls[position].isNotEmpty()) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(podcastUrls[position])
                    prepareAsync()
                    setOnPreparedListener {
                        duration = it.duration
                        progressBar.max = duration
                        durationText.text = formatTime(duration)
                        statusText.text = "准备播放"
                        statusText.setTextColor(getColor(R.color.paused))
                        isPlaying = false
                        updatePlayButton()
                    }
                    setOnCompletionListener {
                        pauseAudio()
                    }
                    setOnErrorListener { _, what, extra ->
                        statusText.text = "播放错误"
                        statusText.setTextColor(getColor(R.color.error))
                        false
                    }
                }
            } else {
                statusText.text = "演示模式 - 无音频源"
                statusText.setTextColor(getColor(R.color.paused))
                isPlaying = false
                updatePlayButton()
            }
        } catch (e: Exception) {
            statusText.text = "加载失败"
            statusText.setTextColor(getColor(R.color.error))
            e.printStackTrace()
        }
    }

    private fun togglePlayPause() {
        if (isPlaying) {
            pauseAudio()
        } else {
            playAudio()
        }
    }

    private fun playAudio() {
        mediaPlayer?.let {
            try {
                it.start()
                isPlaying = true
                statusText.text = "正在播放..."
                statusText.setTextColor(getColor(R.color.playing))
                updatePlayButton()
                handler.post(progressUpdateRunnable)
            } catch (e: Exception) {
                statusText.text = "播放失败"
                statusText.setTextColor(getColor(R.color.error))
                e.printStackTrace()
            }
        } ?: run {
            statusText.text = "没有可播放的音频"
            statusText.setTextColor(getColor(R.color.error))
        }
    }

    private fun pauseAudio() {
        mediaPlayer?.pause()
        isPlaying = false
        statusText.text = "已暂停"
        statusText.setTextColor(getColor(R.color.paused))
        updatePlayButton()
        handler.removeCallbacks(progressUpdateRunnable)
    }

    private fun updateProgress() {
        mediaPlayer?.let {
            currentPosition = it.currentPosition
            if (currentPosition < duration) {
                progressBar.progress = currentPosition
                currentTimeText.text = formatTime(currentPosition)
                handler.postDelayed(progressUpdateRunnable, 1000)
            } else {
                pauseAudio()
            }
        }
    }

    private fun formatTime(millis: Int): String {
        val minutes = millis / 60000
        val seconds = (millis % 60000) / 1000
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun updatePlayButton() {
        playBtn.setImageResource(
            if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        handler.removeCallbacks(progressUpdateRunnable)
    }
}