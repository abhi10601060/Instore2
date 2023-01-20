package com.example.instore2.utility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.instore2.repos.StorageRepo
import com.example.instore2.viewmodels.StorageViewModel

class StorageViewModelFactory(private val repo : StorageRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StorageViewModel(repo) as T
    }
}