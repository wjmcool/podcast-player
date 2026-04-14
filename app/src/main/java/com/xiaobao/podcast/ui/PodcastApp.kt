package com.xiaobao.podcast.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.xiaobao.podcast.data.model.Episode
import com.xiaobao.podcast.ui.components.*
import com.xiaobao.podcast.ui.screens.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastApp(viewModel: PlayerViewModel = viewModel()) {
    val currentEpisode by viewModel.currentEpisode.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val playbackState by viewModel.playbackState.collectAsState()

    Scaffold(
        containerColor = com.xiaobao.podcast.ui.theme.BgDark,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("🎙️ 小宝播客", fontWeight = FontWeight.Bold, color = Color.White)
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = com.xiaobao.podcast.ui.theme.PurplePrimary
                )
            )
        },
        bottomBar = {
            if (currentEpisode != null) {
                MiniPlayer(
                    episode = currentEpisode!!,
                    isPlaying = isPlaying,
                    progress = progress,
                    onPlayPause = { viewModel.togglePlayPause() },
                    onNext = { viewModel.playNext() },
                    onPrev = { viewModel.playPrev() },
                    onClick = { /* could expand to full player */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Text(
                    "📻 精选节目",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = com.xiaobao.podcast.ui.theme.PurpleLight,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            itemsIndexed(viewModel.episodes) { index, episode ->
                EpisodeCard(
                    episode = episode,
                    isActive = currentEpisode?.id == episode.id,
                    onClick = { viewModel.playEpisode(index) }
                )
            }
        }
    }
}
