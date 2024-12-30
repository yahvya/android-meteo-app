package yahvya.meteo_app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import yahvya.meteo_app.database.daos.FavoritesDao
import yahvya.meteo_app.database.entities.FavoritesEntity

@Database(entities = [FavoritesEntity::class], version = 1)
abstract class WeatherAppDatabase : RoomDatabase() {
    /**
     * @return favorite dao
     */
    abstract fun favoritesDao(): FavoritesDao
}