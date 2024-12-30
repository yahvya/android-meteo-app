package yahvya.meteo_app.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var serializedContent: String
)