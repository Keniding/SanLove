package com.keniding.sanlove.domain.utils

import android.content.Context
import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ImageCache(private val context: Context) {
    private val maxCacheSize = 50L * 1024 * 1024 // 50MB
    private val cacheDir = File(context.cacheDir, "image_cache")

    init {
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        maintainCacheSize()
    }

    fun getCachedFile(url: String): File? {
        val file = File(cacheDir, url.hashCode().toString())
        return if (file.exists()) file else null
    }

    suspend fun cacheImage(url: String, bitmap: Bitmap) = withContext(Dispatchers.IO) {
        val file = File(cacheDir, url.hashCode().toString())
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        maintainCacheSize()
    }

    private fun maintainCacheSize() {
        if (cacheDir.totalSpace > maxCacheSize) {
            val files = cacheDir.listFiles() ?: return
            files.sortBy { it.lastModified() }
            var spaceToFree = cacheDir.totalSpace - maxCacheSize
            for (file in files) {
                if (spaceToFree <= 0) break
                spaceToFree -= file.length()
                file.delete()
            }
        }
    }
}
