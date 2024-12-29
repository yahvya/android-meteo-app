package yahvya.meteo_app.apis

import android.content.Context
import okhttp3.Cache

/**
 * @brief build a cache instance
 */
object CacheConfig{
    /**
     * @return a cache instance
     */
    fun getCacheInstance(context: Context) = Cache(
        context.cacheDir,
        10 * 1024 * 1024 // from my search to have 10MB cache
    )
}