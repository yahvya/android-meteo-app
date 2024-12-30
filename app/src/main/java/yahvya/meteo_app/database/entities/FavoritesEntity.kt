package yahvya.meteo_app.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val longitude: String,
    val latitude: String,
    val cityName: String?
)