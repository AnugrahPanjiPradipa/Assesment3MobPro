package id.Panji.Assesment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nominal")
data class Nominal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nominal: Int,
    val kategori: String,
    var selectedOption:String="",
    val tanggal: String
)
