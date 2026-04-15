package com.podcast.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.io.Serializable

class EpisodeAdapter(
    private val episodes: List<Episode>,
    private val onItemClick: (Episode) -> Unit
) : RecyclerView.Adapter<EpisodeAdapter.ViewHolder>(), Serializable {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.episodeImage)
        val titleText: TextView = view.findViewById(R.id.episodeTitle)
        val metaText: TextView = view.findViewById(R.id.episodeMeta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_episode, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val episode = episodes[position]
        holder.titleText.text = episode.title
        holder.metaText.text = "${episode.author} • ${episode.duration}"
        Picasso.get().load(episode.imageUrl).into(holder.imageView)
        holder.itemView.setOnClickListener { onItemClick(episode) }
    }

    override fun getItemCount() = episodes.size
}