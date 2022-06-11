package com.example.cypresspracticaltask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cypresspracticaltask.R
import com.example.cypresspracticaltask.databinding.AlbumAdapterItemBinding
import com.example.cypresspracticaltask.models.Album
import com.example.cypresspracticaltask.models.ImageModel
import com.example.cypresspracticaltask.repositories.AlbumRepository
import kotlinx.coroutines.*

class AlbumsAdapter() :
    RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>() {

    private var albums: List<Album>? = null

    fun updateAlbumsList(albums: List<Album>) {
        this.albums = albums
    }

    inner class AlbumsViewHolder(var binding: AlbumAdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val holder = AlbumsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.album_adapter_item, parent, false
            )
        )
        return holder
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        val managedPosition = position % albums!!.size
        val album = albums?.get(managedPosition)
        holder.binding.albumTitle.text = album?.title ?: "Error"

        holder.binding.albumImagesRecycler.layoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        holder.binding.albumImagesRecycler.adapter = ImageAdapter(album?.imageList)
    }

    override fun getItemCount(): Int {
        return if (albums.isNullOrEmpty()) 0 else Int.MAX_VALUE
    }
}