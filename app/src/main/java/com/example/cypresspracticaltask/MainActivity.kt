package com.example.cypresspracticaltask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cypresspracticaltask.adapters.AlbumsAdapter
import com.example.cypresspracticaltask.databinding.ActivityMainBinding
import com.example.cypresspracticaltask.interfaces.ApiInterface
import com.example.cypresspracticaltask.managers.RetrofitManager
import com.example.cypresspracticaltask.repositories.AlbumRepository
import com.example.cypresspracticaltask.viewModelFactories.MainViewModelFactory
import com.example.cypresspracticaltask.viewmodels.MainActivityViewModel

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
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(AlbumRepository(apiInterface))
        )[MainActivityViewModel::class.java]
        setObservers()
    }

    private fun setObservers() {
        viewModel.albums.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter.updateAlbumsList(it)
                adapter.notifyDataSetChanged()
            }
        })
    }
}