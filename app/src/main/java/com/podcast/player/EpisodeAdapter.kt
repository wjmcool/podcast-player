package com.podcast.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.podcast.player.data.Episode
import com.squareup.picasso.Picasso

class EpisodeAdapter(
    private val episodes: List<Episode>,
    private val onClick: (Episode) -> Unit
) : RecyclerView.Adapter<EpisodeAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.episodeImage)
        val title: TextView = view.findViewById(R.id.episodeTitle)
        val meta: TextView = view.findViewById(R.id.episodeMeta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val ep = episodes[position]
        holder.title.text = ep.title
        holder.meta.text = ep.author + " - " + ep.duration
        Picasso.get().load(ep.imageUrl).into(holder.img)
        holder.itemView.setOnClickListener { onClick(ep) }
    }

    override fun getItemCount() = episodes.size
}