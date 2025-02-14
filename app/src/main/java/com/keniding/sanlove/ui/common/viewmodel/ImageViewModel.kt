package com.keniding.sanlove.ui.common.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.keniding.sanlove.data.repository.ImageRepository
import com.keniding.sanlove.data.repository.ImageType
import com.keniding.sanlove.data.worker.ImageUploadWorker
import com.keniding.sanlove.domain.utils.ImageCache
import com.keniding.sanlove.domain.utils.UploadStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ImageViewModel(
    private val imageRepository: ImageRepository,
    private val imageCache: ImageCache,
    private val workManager: WorkManager
) : ViewModel() {

    private val _uploadStatus = MutableStateFlow<UploadStatus>(UploadStatus.Idle)
    val uploadStatus: StateFlow<UploadStatus> = _uploadStatus.asStateFlow()

    fun uploadImage(uri: Uri, imageType: ImageType) {
        viewModelScope.launch {
            _uploadStatus.value = UploadStatus.Loading

            try {
                when (val result = imageRepository.processAndGetCdnUrl(uri, imageType)) {
                    is ImageRepository.ImageUploadResult.Success -> {
                        scheduleUpload()
                        _uploadStatus.value = UploadStatus.Success(result.cdnUrl)
                    }
                    is ImageRepository.ImageUploadResult.Error -> {
                        _uploadStatus.value = UploadStatus.Error(result.message)
                    }
                }
            } catch (e: Exception) {
                _uploadStatus.value = UploadStatus.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun scheduleUpload() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val uploadWorkRequest = OneTimeWorkRequestBuilder<ImageUploadWorker>()
            .setConstraints(constraints)
            .build()

        workManager.enqueue(uploadWorkRequest)
    }
}
