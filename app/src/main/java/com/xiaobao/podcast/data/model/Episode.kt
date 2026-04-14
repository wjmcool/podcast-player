package com.xiaobao.podcast.data.model

data class Episode(
    val id: String,
    val title: String,
    val author: String,
    val description: String,
    val coverUrl: String,
    val audioUrl: String,
    val duration: Long, // in milliseconds
    val publishedAt: String = ""
)
