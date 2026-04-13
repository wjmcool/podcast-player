package com.openclaw.podcast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclaw.podcast.R
import com.openclaw.podcast.databinding.ItemPodcastBinding
import com.openclaw.podcast.model.Podcast

class PodcastAdapter(
    private val podcasts: List<Podcast>,
    private val onItemClick: (Podcast) -> Unit
) : RecyclerView.Adapter<PodcastAdapter.PodcastViewHolder>() {

    class PodcastViewHolder(
        private val binding: ItemPodcastBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(podcast: Podcast, onItemClick: (Podcast) -> Unit) {
            binding.apply {
                podcastTitle.text = podcast.title
                podcastDescription.text = podcast.description
                podcastIcon.text = podcast.icon
                root.setOnClickListener { onItemClick(podcast) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastViewHolder {
        val binding = ItemPodcastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PodcastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PodcastViewHolder, position: Int) {
        holder.bind(podcasts[position], onItemClick)
    }

    override fun getItemCount() = podcasts.size
}
