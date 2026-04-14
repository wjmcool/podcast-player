package com.xiaobao.podcast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.xiaobao.podcast.ui.PodcastApp
import com.xiaobao.podcast.ui.theme.PodcastTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PodcastTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PodcastApp()
                }
            }
        }
    }
}
