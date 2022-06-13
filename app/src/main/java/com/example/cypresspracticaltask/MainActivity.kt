package com.example.cypresspracticaltask

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cypresspracticaltask.adapters.AlbumsAdapter
import com.example.cypresspracticaltask.databinding.ActivityMainBinding
import com.example.cypresspracticaltask.interfaces.ApiInterface
import com.example.cypresspracticaltask.managers.RetrofitManager
import com.example.cypresspracticaltask.repositories.AlbumRepository
import com.example.cypresspracticaltask.roomDb.AlbumDatabase
import com.example.cypresspracticaltask.viewModelFactories.MainViewModelFactory
import com.example.cypresspracticaltask.viewmodels.MainActivityViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: AlbumsAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        showLoading()
        val apiInterface = RetrofitManager.getInstance().create(ApiInterface::class.java)
        initializeViewModel(apiInterface)
        initializeRecyclerViewAndAdapters()
    }

    private fun showLoading() {
        binding.shimmerlayout.startShimmer()
        binding.shimmerlayout.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
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
            MainViewModelFactory(AlbumRepository(apiInterface, albumDao))
        )[MainActivityViewModel::class.java]
        setObservers()
    }

    private fun setObservers() {
        viewModel.isInternetAvailable.observe(this, Observer {
            if (it) {
                observeOnLatestData()
            } else {
                observeOnLocalData()
            }
        })

    }

    private fun observeOnLatestData() {
        viewModel.localAlbums.removeObservers(this)
        viewModel.albums.observe(this, Observer {
            if (it != null)
                if (it.isNotEmpty()) {
                    adapter.updateAlbumsList(it)
                    adapter.notifyDataSetChanged()
                    viewModel.fetchImagesForAlbum(it, adapter)
                    showRecycler()
                }
        })
    }

    private fun showRecycler() {
        binding.shimmerlayout.stopShimmer()
        binding.shimmerlayout.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun observeOnLocalData() {
        viewModel.albums.removeObservers(this)
        viewModel.localAlbums.observe(this, Observer {
            if (it != null)
                if (it.isNotEmpty()) {
                    Toast.makeText(
                        this,
                        "Yor device is not connected to the internet",
                        Toast.LENGTH_SHORT
                    ).show()
                    adapter.updateAlbumsList(it)
                    adapter.notifyDataSetChanged()
                    showRecycler()
                } else {
                    if (viewModel.isInternetAvailable.value == false) {
                        displayAlert()
                    }
                }
        })
    }

    private fun displayAlert() {
        binding.shimmerlayout.stopShimmer()
        binding.shimmerlayout.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Saved Local data is unavailable \nPlease come back once you have stable internet connection")
            .setCancelable(false)
            .setPositiveButton("Exit") { dialog, _ ->
                dialog.dismiss()
                finish()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("No Data Available")
        alert.show()
    }
}