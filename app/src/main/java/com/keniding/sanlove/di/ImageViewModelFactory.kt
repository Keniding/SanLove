package com.keniding.sanlove.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.keniding.sanlove.data.repository.ImageRepository
import com.keniding.sanlove.domain.utils.ImageCache
import com.keniding.sanlove.ui.common.viewmodel.ImageViewModel

class ImageViewModelFactory(
    private val repository: ImageRepository,
    private val imageCache: ImageCache,
    private val workManager: WorkManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ImageViewModel(repository, imageCache, workManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
