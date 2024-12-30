package yahvya.meteo_app.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import yahvya.meteo_app.database.entities.FavoritesEntity

@Dao
interface FavoritesDao {
    @Insert
    suspend fun insert(favoritesEntity: FavoritesEntity):Long

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun delete(id: Int)

    @Query(value = "SELECT * from favorites")
    suspend fun getAll(): List<FavoritesEntity>

    @Query(value = "SELECT * FROM favorites ORDER BY id DESC LIMIT :countOfItems")
    suspend fun getSomeones(countOfItems: Int): List<FavoritesEntity>
}