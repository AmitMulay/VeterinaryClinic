package com.vclinic.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.LruCache
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object ImageLoader {
    private var lruCache: LruCache<String, Bitmap>
    init {
        val memorySize: Long = Runtime.getRuntime().maxMemory() / 1024
        val cacheSize: Int = (memorySize / 4).toInt()
        lruCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, bitmap: Bitmap?): Int {
                return (bitmap?.rowBytes ?: 0) * (bitmap?.height ?: 0) / 1024
            }
        }
    }


    fun showImage(url: String, imageView: ImageView) {
        var executor: ExecutorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        val cached = lruCache.get(url)
        if (cached != null) {
            update(imageView, cached)
            return
        }
        imageView.tag = url
        executor.submit {
            val bitmap: Bitmap? =downloadImage(url)
            if (bitmap != null) {
                if (imageView.tag == url) {
                    update(imageView,bitmap)
                }
                lruCache.put(url, bitmap)
            }
        }
    }

    private fun downloadImage(mainUrl: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val url = URL(mainUrl)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            bitmap = BitmapFactory.decodeStream(conn.inputStream)
            conn.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }

    private fun update(imageView: ImageView, bitmap: Bitmap) {
        val handler: Handler = Handler(Looper.getMainLooper())
        handler.post {
            imageView.setImageBitmap(bitmap)
        }
    }


}