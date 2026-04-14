package com.podcastplayer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.podcastplayer.databinding.ItemEpisodeBinding

data class Episode(
    val id: Int,
    val title: String,
    val description: String,
    val duration: String,
    val durationMs: Long,
    val audioUrl: String,
    val publishDate: String
)

class EpisodesAdapter(
    private val episodes: List<Episode>,
    private val onClick: (Episode) -> Unit
) : RecyclerView.Adapter<EpisodesAdapter.VH>() {

    inner class VH(private val b: ItemEpisodeBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(e: Episode) {
            b.tvEpisodeNumber.text = e.id.toString()
            b.tvEpisodeTitle.text = e.title
            b.tvDuration.text = e.duration
            b.root.setOnClickListener { onClick(e) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount() = episodes.size
}
