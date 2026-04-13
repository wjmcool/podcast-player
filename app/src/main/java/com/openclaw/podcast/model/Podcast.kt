package com.openclaw.podcast.model

data class Podcast(
    val id: Int,
    val title: String,
    val description: String,
    val icon: String,
    val imageUrl: String,
    val audioUrl: String,
    val duration: Long
)
