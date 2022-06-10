package com.example.cypresspracticaltask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cypresspracticaltask.R
import com.example.cypresspracticaltask.databinding.AlbumAdapterItemBinding
import com.example.cypresspracticaltask.models.Album

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>() {

    private var albums: List<Album>? = null

    fun updateAlbumsList(albums: List<Album>) {
        this.albums = albums
    }

    inner class AlbumsViewHolder(var binding: AlbumAdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        return AlbumsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.album_adapter_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        val album = albums?.get(position % albums!!.size)
        holder.binding.albumName.text = album?.title ?: "Error"
    }

    override fun getItemCount(): Int {
//        return albums?.size ?: 0
        return if (albums.isNullOrEmpty()) 0 else Int.MAX_VALUE
    }
}