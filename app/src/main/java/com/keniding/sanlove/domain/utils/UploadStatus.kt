package com.keniding.sanlove.domain.utils

sealed class UploadStatus {
    data object Idle : UploadStatus()
    data object Loading : UploadStatus()
    data class Success(val url: String) : UploadStatus()
    data class Error(val message: String) : UploadStatus()
}
