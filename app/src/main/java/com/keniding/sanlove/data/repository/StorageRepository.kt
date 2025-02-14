package com.keniding.sanlove.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ImageRepository(private val context: Context) {
    companion object {
        private const val BASE_CDN_URL = "https://raw.githubusercontent.com/Keniding/app-cdn-images/main/images"
        private const val COMPRESSION_QUALITY = 80
        private const val MAX_WIDTH = 1080
        private const val MAX_HEIGHT = 1080
        private const val MAX_FILE_SIZE_BYTES = 1024 * 1024 // 1MB
    }

    sealed class ImageUploadResult {
        data class Success(val cdnUrl: String) : ImageUploadResult()
        data class Error(val message: String) : ImageUploadResult()
    }

    suspend fun processAndGetCdnUrl(
        imageUri: Uri,
        imageType: ImageType = ImageType.POST
    ): ImageUploadResult = withContext(Dispatchers.IO) {
        try {
            val tempFile = createTempFile(imageUri)
            val compressedFile = compressImage(tempFile, imageType)

            val fileName = generateFileName(imageType)

            val outputDir = File(context.filesDir, "pending_uploads/${imageType.folder}")
            outputDir.mkdirs()

            val finalFile = File(outputDir, fileName)
            compressedFile.copyTo(finalFile, overwrite = true)

            val cdnUrl = "$BASE_CDN_URL/${imageType.folder}/$fileName"

            tempFile.delete()
            compressedFile.delete()

            ImageUploadResult.Success(cdnUrl)
        } catch (e: Exception) {
            Log.e("ImageRepository", "Error processing image", e)
            ImageUploadResult.Error(e.message ?: "Unknown error")
        }
    }

    private suspend fun compressImage(
        originalFile: File,
        imageType: ImageType
    ): File = withContext(Dispatchers.IO) {
        Compressor.compress(context, originalFile) {
            quality(COMPRESSION_QUALITY)
            resolution(MAX_WIDTH, MAX_HEIGHT)
            format(Bitmap.CompressFormat.JPEG)
            size(MAX_FILE_SIZE_BYTES.toLong())
        }
    }

    private fun createTempFile(uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("temp_", ".jpg", context.cacheDir)

        FileOutputStream(tempFile).use { outputStream ->
            inputStream?.copyTo(outputStream)
        }

        return tempFile
    }

    private fun generateFileName(imageType: ImageType): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(Date())
        val random = UUID.randomUUID().toString().substring(0, 8)
        return "${imageType.prefix}_${timestamp}_${random}.jpg"
    }
}

enum class ImageType(val folder: String, val prefix: String) {
    PROFILE("profile", "prof"),
    POST("posts", "post"),
    THUMBNAIL("thumbnails", "thumb")
}

fun File.sizeInMb(): Double = length().toDouble() / (1024 * 1024)
