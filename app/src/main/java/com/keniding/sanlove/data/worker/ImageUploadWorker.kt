package com.keniding.sanlove.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.keniding.sanlove.data.uploader.GitUploader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageUploadWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val gitUploader = GitUploader(applicationContext)

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val pendingFiles = gitUploader.getPendingUploads()

            pendingFiles.forEach { file ->
                val relativePath = gitUploader.getRelativePath(file)
                gitUploader.markAsUploaded(file)
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
