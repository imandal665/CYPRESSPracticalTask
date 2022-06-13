package com.example.cypresspracticaltask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cypresspracticaltask.R
import com.example.cypresspracticaltask.databinding.ImageAdapterItemBinding
import com.example.cypresspracticaltask.models.ImageModel
import com.squareup.picasso.Picasso

class ImageAdapter(private var imageList: List<ImageModel>?) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(var binding: ImageAdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.image_adapter_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = imageList?.get(position % imageList!!.size)
        val url = image?.thumbnailUrl ?: ""
        Picasso.get().load(url)
            .into(holder.binding.imageView);

    }

    override fun getItemCount(): Int {
        return if (imageList.isNullOrEmpty()) 0 else Int.MAX_VALUE
    }
}