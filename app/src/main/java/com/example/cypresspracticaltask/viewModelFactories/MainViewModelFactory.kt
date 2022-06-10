package com.example.cypresspracticaltask.viewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cypresspracticaltask.interfaces.ApiInterface
import com.example.cypresspracticaltask.repositories.AlbumRepository
import com.example.cypresspracticaltask.viewmodels.MainActivityViewModel

class MainViewModelFactory(private val repository: AlbumRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository) as T
    }
}