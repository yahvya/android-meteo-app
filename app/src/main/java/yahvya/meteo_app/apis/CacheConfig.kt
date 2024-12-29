package yahvya.meteo_app.apis

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

/**
 * @brief cache instance builder
 */
object CacheConfig{
    /**
     * @brief build
     * @param context context
     * @return a cache instance
     */
    fun getCacheInstance(context: Context,subfolder: String):Cache{
        val cachePath = File("${context.cacheDir.path}/${subfolder}")

        if(!cachePath.isDirectory)
            cachePath.mkdir()

        return Cache(
            cachePath,
            (10 * 1024 * 1024).toLong() // from my search to have 10MB cache
        )
    }

    /**
     * @brief build http client
     * @param context context
     * @param apiName api name
     * @return the created client
     */
    fun buildHttpClient(context: Context,apiName: String):OkHttpClient{
        val okHttpClient = OkHttpClient.Builder()
            .cache(getCacheInstance(context= context,subfolder= apiName))
            .addInterceptor { chain ->
                var request = chain.request()

                request = if (isUserConnected(context))
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()

                chain.proceed(request)
            }
            .build()

        return okHttpClient
    }

    /**
     * @brief check if the user is connected
     * @param context context
     * @return if user is connected
     */
    fun isUserConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}