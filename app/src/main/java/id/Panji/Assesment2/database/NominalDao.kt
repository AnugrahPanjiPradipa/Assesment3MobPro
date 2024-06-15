package id.Panji.Assesment2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import id.Panji.Assesment2.model.Nominal

@Dao
interface NominalDao {

    @Insert
    suspend fun insert(nominal: Nominal)

    @Update
    suspend fun update(nominal: Nominal)

    @Query("SELECT * FROM nominal ORDER BY kategori ASC")
    fun getNominal(): Flow<List<Nominal>>

    @Query("SELECT * FROM nominal WHERE id = :id")
    suspend fun getNominalById(id: Long): Nominal?

    @Query("DELETE FROM nominal WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM nominal ORDER BY tanggal")
    fun getNominalSortedByDateAscending(): Flow<List<Nominal>>

    @Query("SELECT * FROM nominal ORDER BY tanggal DESC")
    fun getNominalSortedByDateDescending(): Flow<List<Nominal>>

}