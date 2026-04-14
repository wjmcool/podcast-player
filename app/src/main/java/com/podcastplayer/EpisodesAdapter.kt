package com.podcastplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNumber: TextView = itemView.findViewById(R.id.tvEpisodeNumber)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvEpisodeTitle)
        private val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)

        fun bind(e: Episode) {
            tvNumber.text = e.id.toString()
            tvTitle.text = e.title
            tvDuration.text = e.duration
            itemView.setOnClickListener { onClick(e) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_episode, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount() = episodes.size
}
