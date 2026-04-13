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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
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

            statusText.text = "准备就绪"
            statusText.setTextColor(getColor(R.color.paused))
        } catch (e: Exception) {
            e.printStackTrace()
            statusText.text = "初始化错误"
        }
    }

    private fun loadPodcast(position: Int) {
        try {
            // 释放之前的媒体播放器
            mediaPlayer?.release()
            mediaPlayer = null

            // 演示模式 - 无实际音频
            statusText.text = "演示模式 - 请添加音频URL"
            statusText.setTextColor(getColor(R.color.paused))
            isPlaying = false
            updatePlayButton()
        } catch (e: Exception) {
            e.printStackTrace()
            statusText.text = "加载失败"
        }
    }

    private fun togglePlayPause() {
        try {
            statusText.text = "演示模式 - 请添加音频URL"
            statusText.setTextColor(getColor(R.color.paused))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun playAudio() {
        try {
            statusText.text = "演示模式 - 请添加音频URL"
            statusText.setTextColor(getColor(R.color.paused))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun pauseAudio() {
        try {
            statusText.text = "已暂停"
            statusText.setTextColor(getColor(R.color.paused))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateProgress() {
        try {
            handler.postDelayed(progressUpdateRunnable, 1000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun formatTime(millis: Int): String {
        val minutes = millis / 60000
        val seconds = (millis % 60000) / 1000
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun updatePlayButton() {
        try {
            playBtn.setImageResource(R.drawable.ic_play)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            mediaPlayer?.release()
            mediaPlayer = null
            handler.removeCallbacks(progressUpdateRunnable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}