package yahvya.meteo_app.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import yahvya.meteo_app.database.entities.FavoritesEntity

@Dao
interface FavoritesDao {
    @Insert
    suspend fun insert(favoritesEntity: FavoritesEntity)

    @Insert
    suspend fun delete(favoritesEntity: FavoritesEntity)

    @Query(value = "SELECT * from favorites")
    suspend fun getAll(): List<FavoritesEntity>

    @Query(value = "SELECT * FROM favorites LIMIT :countOfItems")
    suspend fun getSomeones(countOfItems: Int): List<FavoritesEntity>
}