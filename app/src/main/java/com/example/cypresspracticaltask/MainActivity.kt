package com.example.cypresspracticaltask

import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.cypresspracticaltask.adapters.AlbumsAdapter
import com.example.cypresspracticaltask.databinding.ActivityMainBinding
import com.example.cypresspracticaltask.interfaces.ApiInterface
import com.example.cypresspracticaltask.managers.RetrofitManager
import com.example.cypresspracticaltask.repositories.AlbumRepository
import com.example.cypresspracticaltask.roomDb.AlbumDatabase
import com.example.cypresspracticaltask.viewModelFactories.MainViewModelFactory
import com.example.cypresspracticaltask.viewmodels.MainActivityViewModel
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: AlbumsAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val apiInterface = RetrofitManager.getInstance().create(ApiInterface::class.java)
        initializeViewModel(apiInterface)
        initializeRecyclerViewAndAdapters()
    }

    private fun initializeRecyclerViewAndAdapters() {
        adapter = AlbumsAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun initializeViewModel(apiInterface: ApiInterface) {
        val albumDao = AlbumDatabase.getDatabase(application).albumDao()
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(AlbumRepository(apiInterface,albumDao))
        )[MainActivityViewModel::class.java]
        setObservers()
    }

    private fun setObservers() {

        viewModel.savedAlbums?.observe(this, Observer {
            Log.d("savedAlbums", it.size.toString())
            Log.d("savedAlbums", it.toString())
        })

        viewModel.albums.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter.updateAlbumsList(it)
                adapter.notifyDataSetChanged()
                viewModel.fetchImagesForAlbum(it, adapter)
            }
        })

    }
}