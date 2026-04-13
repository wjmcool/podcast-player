package com.example.podcastplayer

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
                if (fromUser) {
                    currentPosition = progress
                    currentTimeText.text = formatTime(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekAudio(currentPosition)
            }
        })

        volumeSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    setVolume(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun loadPodcast(position: Int) {
        val podcastName = podcastItems[position].split(" - ")[0]
        statusText.text = "准备播放: $podcastName"
        statusText.setTextColor(getColor(R.color.paused))
        isPlaying = false
        progressBar.progress = 0
        currentTimeText.text = "00:00"
        updatePlayButton()
    }

    private fun togglePlayPause() {
        if (isPlaying) {
            pauseAudio()
        } else {
            playAudio()
        }
    }

    private fun playAudio() {
        isPlaying = true
        statusText.text = "正在播放..."
        statusText.setTextColor(getColor(R.color.playing))
        updatePlayButton()
        handler.post(progressUpdateRunnable)
    }

    private fun pauseAudio() {
        isPlaying = false
        statusText.text = "已暂停"
        statusText.setTextColor(getColor(R.color.paused))
        updatePlayButton()
        handler.removeCallbacks(progressUpdateRunnable)
    }

    private fun updateProgress() {
        if (isPlaying) {
            currentPosition = (progressBar.progress + 1).coerceAtMost(duration)
            progressBar.progress = currentPosition
            currentTimeText.text = formatTime(currentPosition)

            if (currentPosition >= duration) {
                pauseAudio()
            } else {
                handler.postDelayed(progressUpdateRunnable, 1000)
            }
        }
    }

    private fun seekAudio(position: Int) {
        currentPosition = position
    }

    private fun setVolume(level: Int) {
        // Audio volume logic would go here
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
}