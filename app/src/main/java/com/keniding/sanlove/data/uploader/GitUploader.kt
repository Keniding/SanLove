package com.keniding.sanlove.data.uploader

import android.content.Context
import java.io.File

class GitUploader(private val context: Context) {
    companion object {
        private const val REPO_NAME = "app-cdn-images"
        private const val BRANCH = "main"
        private const val IMAGES_PATH = "images"
    }

    fun getPendingUploads(): List<File> {
        val pendingDir = File(context.filesDir, "pending_uploads")
        return pendingDir.walkTopDown()
            .filter { it.isFile }
            .toList()
    }

    fun getRelativePath(file: File): String {
        val pendingDir = File(context.filesDir, "pending_uploads")
        return file.absolutePath
            .removePrefix(pendingDir.absolutePath)
            .removePrefix("/")
    }

    fun markAsUploaded(file: File) {
        if (file.exists()) {
            file.delete()
        }
    }
}
